package im.fooding.core.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.core.model.ddd.DddEvent;
import im.fooding.core.repository.ddd.DddEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EventProducerService {
    private final DddEventRepository dddEventRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC;

    public EventProducerService(DddEventRepository dddEventRepository,
                                ObjectMapper objectMapper,
                                KafkaTemplate<String, String> kafkaTemplate,
                                @Value("${kafka.internal.topic}") String topic) {
        this.dddEventRepository = dddEventRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.TOPIC = topic;
    }

    @Transactional
    public <T> void publishEvent(String eventType, T eventData) {
        try {
            String dataJson = objectMapper.writeValueAsString(eventData);

            DddEvent dddEvent = DddEvent.builder()
                    .eventType(eventType)
                    .data(dataJson)
                    .build();

            DddEvent savedEvent = dddEventRepository.save(dddEvent);
            String message = createCDCStyleMessage(eventType, eventData);
            kafkaTemplate.send(TOPIC, message);
            log.info("Published Event with id: {}", savedEvent.getId());
        } catch (JsonProcessingException e) {
            log.error("Error serializing {}", eventType, e);
            throw new RuntimeException("Failed to publish " + eventType, e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void publishEvent(String topic, String key, Object eventData) {
        try {
            String message = objectMapper.writeValueAsString(eventData);
            kafkaTemplate.send(topic, key, message);
            log.info("토픽으로 메시지를 발행했습니다: {}, key: {}", topic, key);
        } catch (JsonProcessingException e) {
            log.error("토픽 메시지 직렬화 중 오류가 발생했습니다: {}", topic, e);
            throw new RuntimeException("메시지 발행에 실패했습니다: " + topic, e);
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
