package im.fooding.core;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.repository.elasticsearch.store.StoreDocumentRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest(classes = FoodingCoreConfiguration.class)
@Transactional
public class TestConfig {
    @MockBean
    protected SlackClient slackClient;

    @MockBean
    protected ElasticsearchOperations elasticsearchOperations;

    @MockBean
    protected StoreDocumentRepository storeDocumentRepository;
}
