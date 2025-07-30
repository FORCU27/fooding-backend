package im.fooding.core.global.elasticsearch.setup;

import im.fooding.core.global.elasticsearch.ElasticsearchIndexInitializer;
import im.fooding.core.global.elasticsearch.IndexSettingMapping;
import im.fooding.core.model.store.document.StoreDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StoreIndexSetup {
    private final ElasticsearchIndexInitializer initializer;
    private final ElasticsearchOperations operations;

    public void createStoreIndex() {
        Map<String, Object> settings = Map.of(
                "index", Map.of(
                        "number_of_shards", 1,
                        "number_of_replicas", 0,
                        "analysis", Map.of(
                                "filter", Map.of(
                                        "edge_ngram_filter", Map.of(
                                                "type", "edge_ngram",
                                                "min_gram", 1,
                                                "max_gram", 20
                                        )
                                ),
                                "analyzer", Map.of(
                                        "autocomplete_index_analyzer", Map.of(
                                                "type", "custom",
                                                "tokenizer", "whitespace",
                                                "filter", List.of("lowercase", "edge_ngram_filter")
                                        ),
                                        "autocomplete_search_analyzer", Map.of(
                                                "type", "custom",
                                                "tokenizer", "whitespace",
                                                "filter", List.of("lowercase")
                                        )
                                )
                        )
                )
        );

        Map<String, Object> mappings = Map.of(
                "properties", Map.of(
                        "id", Map.of("type", "long"),
                        "name", Map.of(
                                "type", "text",
                                "analyzer", "autocomplete_index_analyzer",
                                "search_analyzer", "autocomplete_search_analyzer"
                        ),
                        "category", Map.of("type", "keyword"),
                        "address", Map.of("type", "text"),
                        "reviewCount", Map.of("type", "integer"),
                        "averageRating", Map.of("type", "double"),
                        "visitCount", Map.of("type", "integer"),
                        "createdAt", Map.of(
                                "type", "date",
                                "format", "yyyy-MM-dd'T'HH:mm:ss.SSS||epoch_millis"
                        )
                )
        );

        initializer.createIndexIfNotExists(StoreDocument.class, new IndexSettingMapping(settings, mappings), operations);
    }
}
