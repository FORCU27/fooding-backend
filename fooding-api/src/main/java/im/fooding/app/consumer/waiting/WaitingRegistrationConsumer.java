package im.fooding.app.consumer.waiting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.app.service.app.waiting.AppWaitingV3Service;
import im.fooding.app.service.user.waiting.UserStoreWaitingV3Service;
import im.fooding.core.event.waiting.StoreWaitingRegisterRequestEvent;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class WaitingRegistrationConsumer {

    private final UserStoreWaitingV3Service userStoreWaitingV3Service;
    private final AppWaitingV3Service appWaitingV3Service;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String DLQ_TOPIC = "waiting-registration-dlq";

    @KafkaListener(topics = "waiting-registration-request", groupId = "waiting-registration-group")
    public void consume(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("웨이팅 등록 요청을 수신했습니다. partition={}, offset={}", partition, offset);

        try {
            StoreWaitingRegisterRequestEvent event = objectMapper.readValue(message, StoreWaitingRegisterRequestEvent.class);

            if (StoreWaitingChannel.ONLINE.getValue().equals(event.channel())) {
                userStoreWaitingV3Service.processRegistration(event);
            } else {
                appWaitingV3Service.processRegistration(event);
            }

        } catch (JsonProcessingException e) {
            log.error("웨이팅 등록 이벤트 파싱에 실패했습니다. DLQ로 전송합니다. message={}", message, e);
            sendToDlq(message, "PARSING_ERROR", e.getMessage());
        } catch (Exception e) {
            log.error("웨이팅 등록 처리에 실패했습니다. DLQ로 전송합니다. message={}", message, e);
            sendToDlq(message, "PROCESSING_ERROR", e.getMessage());
        }
    }

    private void sendToDlq(String message, String errorType, String errorMessage) {
        ProducerRecord<String, String> record = new ProducerRecord<>(DLQ_TOPIC, message);
        record.headers().add(new RecordHeader("error-type", errorType.getBytes(StandardCharsets.UTF_8)));
        if (errorMessage != null) {
            record.headers().add(new RecordHeader("error-message", errorMessage.getBytes(StandardCharsets.UTF_8)));
        }
        kafkaTemplate.send(record);
    }
}
