package im.fooding.core.global.elasticsearch;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

public interface ElasticsearchIndexInitializer {
    <T> void createIndexIfNotExists(Class<T> documentClass, IndexSettingMapping settingMapping, ElasticsearchOperations operations);
}
