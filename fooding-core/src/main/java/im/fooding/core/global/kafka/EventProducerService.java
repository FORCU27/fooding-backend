package im.fooding.core.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.core.event.store.StoreCreatedEvent;
import im.fooding.core.model.ddd.DddEvent;
import im.fooding.core.repository.ddd.DddEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducerService {
    private final DddEventRepository dddEventRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "fooding.event.internal";

    @Transactional
    public void publishEventA(StoreCreatedEvent storeCreatedEvent) {
        try {
            String dataJson = objectMapper.writeValueAsString(storeCreatedEvent);

            DddEvent dddEvent = DddEvent.builder()
                    .eventType("StoreCreatedEvent")
                    .data(dataJson)
                    .build();
            DddEvent savedEvent = dddEventRepository.save(dddEvent);
            String message = createCDCStyleMessage("StoreCreatedEvent", storeCreatedEvent);
            kafkaTemplate.send(TOPIC, message);
            log.info("Published StoreCreatedEvent with id: {}", savedEvent.getId());

        } catch (JsonProcessingException e) {
            log.error("Error serializing EventA", e);
            throw new RuntimeException("Failed to publish EventA", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 제네릭 메서드로 통합 버전
    @Transactional
    public <T> void publishEvent(String eventType, T eventData) {
        try {
            String dataJson = objectMapper.writeValueAsString(eventData);

            DddEvent dddEvent = DddEvent.builder()
                    .eventType(eventType)
                    .data(dataJson)
                    .build();

            DddEvent savedEvent = dddEventRepository.save(dddEvent);
            log.info("Published {} with id: {}", eventType, savedEvent.getId());

        } catch (JsonProcessingException e) {
            log.error("Error serializing {}", eventType, e);
            throw new RuntimeException("Failed to publish " + eventType, e);
        }
    }

    // CDC 구조와 동일한 메시지 포맷 생성
    private String createCDCStyleMessage(String eventType, Object eventData) throws Exception {
        String dataJson = objectMapper.writeValueAsString(eventData);

        // CDC 메시지 구조와 동일하게 생성
        String cdcMessage = String.format("""
            {
              "schema": {
                "type": "struct"
              },
              "payload": {
                "before": null,
                "after": {
                  "id": %d,
                  "event_type": "%s",
                  "data": "%s",
                  "created_at": %d
                },
                "source": {
                  "table": "ddd_event",
                  "op": "c"
                }
              }
            }
            """,
                System.currentTimeMillis(), // 가짜 ID
                eventType.replace("\"", "\\\""),
                dataJson.replace("\"", "\\\""), // JSON 이스케이프
                System.currentTimeMillis()
        );

        return cdcMessage;
    }
}
