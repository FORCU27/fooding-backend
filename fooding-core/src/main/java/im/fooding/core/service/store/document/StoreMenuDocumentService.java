package im.fooding.core.service.store.document;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import im.fooding.core.model.store.document.StoreMenuDocument;
import im.fooding.core.repository.elasticsearch.store.StoreMenuDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreMenuDocumentService {
    private final StoreMenuDocumentRepository repository;
    private final ElasticsearchOperations elasticsearchOperations;

    public void save(StoreMenuDocument menuDocument) { repository.save( menuDocument ); }

    public void delete( String id ) { repository.deleteById( id ); }

    public StoreMenuDocument findById( String id ){
        Query query = MatchQuery.of(m -> m
                .field( "id" )
                .query( id )
        )._toQuery();

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery( query )
                .build();
        SearchHits<StoreMenuDocument> searchHits = elasticsearchOperations.search( nativeQuery, StoreMenuDocument.class );
        if( searchHits.hasSearchHits() ){
            return searchHits.getSearchHit(0).getContent();
        }
        return null;
    }

    public List<StoreMenuDocument> findByStoreId( String storeId ){
        Query query = MatchQuery.of( m -> m
                .field( "storeId" )
                .query( storeId )
        )._toQuery();

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery( query )
                .build();
        SearchHits<StoreMenuDocument> searchHits = elasticsearchOperations.search( nativeQuery, StoreMenuDocument.class );
        List<StoreMenuDocument> content = searchHits.get().map(SearchHit::getContent).toList();

        return content;
    }

}
