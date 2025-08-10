package im.fooding.core.global.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "test-topic", groupId = "test-group-${spring.profiles.active}")
    public void listenTestTopic(@Header(KafkaHeaders.GROUP_ID) String groupId,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                String message) {
        log.info("groupId: {}, topic: {}, message: {}", groupId, topic, message);
    }
}
