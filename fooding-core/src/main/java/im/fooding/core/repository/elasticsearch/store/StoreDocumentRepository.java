package im.fooding.core.repository.elasticsearch.store;

import im.fooding.core.model.store.document.StoreDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Profile("!test")
public interface StoreDocumentRepository extends ElasticsearchRepository<StoreDocument,String> {
}
