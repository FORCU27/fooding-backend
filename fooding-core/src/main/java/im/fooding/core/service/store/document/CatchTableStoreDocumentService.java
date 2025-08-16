package im.fooding.core.service.store.document;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import im.fooding.core.model.store.document.CatchTableStoreDocument;
import im.fooding.core.repository.elasticsearch.store.CatchTableStoreDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatchTableStoreDocumentService {
    private final CatchTableStoreDocumentRepository repository;
    private final ElasticsearchOperations elasticsearchOperations;

    public String save(CatchTableStoreDocument document){
        return repository.save( document ).getId();
    }

    public void delete( String id ) { repository.deleteById( id ); }

    public CatchTableStoreDocument findById( String id ){
        return repository.findById( id ).orElseThrow();
    }
}
