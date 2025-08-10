package im.fooding.core;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.kafka.KafkaConsumer;
import im.fooding.core.global.kafka.KafkaProducer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(classes = FoodingCoreConfiguration.class)
@Transactional
public class TestConfig {
    @MockBean
    protected SlackClient slackClient;

    @MockBean
    protected KafkaProducer kafkaProducer;

    @MockBean
    protected KafkaConsumer kafkaConsumer;
}
