package im.fooding.core.repository.elasticsearch.store;

import im.fooding.core.model.store.document.StoreMenuDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Profile("!test")
public interface StoreMenuDocumentRepository extends ElasticsearchRepository<StoreMenuDocument, String> {
}
