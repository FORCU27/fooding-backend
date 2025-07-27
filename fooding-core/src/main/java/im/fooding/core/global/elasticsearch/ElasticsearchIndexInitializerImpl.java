package im.fooding.core.global.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ElasticsearchIndexInitializerImpl implements ElasticsearchIndexInitializer {

    @Override
    public <T> void createIndexIfNotExists(Class<T> documentClass, IndexSettingMapping settingMapping, ElasticsearchOperations operations) {
        IndexOperations indexOps = operations.indexOps(documentClass);
        if (!indexOps.exists()) {
            indexOps.create(settingMapping.getSettings());
            indexOps.putMapping(Document.from(settingMapping.getMappings()));
            log.info("new elasticsearch index created : {} ", indexOps.getSettings());
        }
    }
}
