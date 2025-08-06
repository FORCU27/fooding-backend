package im.fooding.core.service.store.document;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.document.StoreDocument;
import im.fooding.core.repository.elasticsearch.store.StoreDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreDocumentService {
    private final StoreDocumentRepository repository;
    private final ElasticsearchOperations elasticsearchOperations;

    public void save(StoreDocument storeDocument) {
        repository.save(storeDocument);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public StoreDocument findById( long id ){
        Query query = MatchQuery.of( m -> m
                .field("id")
                .query( id )
        )._toQuery();

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery( query )
                .build();
        SearchHits<StoreDocument> searchHits = elasticsearchOperations.search( nativeQuery, StoreDocument.class );
        if( searchHits.hasSearchHits() ){
            return searchHits.getSearchHit(0).getContent();
        }
        return null;
    }

    public Page<StoreDocument> fullTextSearch(String searchString, StoreSortType sortType, SortDirection direction, Pageable pageable) {
        SortOrder sortOrder = (direction == SortDirection.ASCENDING) ? SortOrder.Asc : SortOrder.Desc;
        String field = switch (sortType) {
            case REVIEW -> "reviewCount";
            case RECENT -> "createdAt";
            case AVERAGE_RATING -> "averageRating";
            case VISIT -> "visitCount";
        };

        Query query;

        if (!StringUtils.hasText(searchString)) {
            query = MatchAllQuery.of(m -> m)._toQuery();
        } else {
            query = MultiMatchQuery.of(m -> m
                    .fields("name")
                    .query(searchString)
                    .operator(Operator.And)
            )._toQuery();
        }

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .withSort(s -> s
                        .field(f -> f
                                .field(field)
                                .order(sortOrder)
                        )
                )
                .withPageable(pageable)
                .build();

        SearchHits<StoreDocument> searchHits = elasticsearchOperations.search(nativeQuery, StoreDocument.class);
        List<StoreDocument> content = searchHits.get().map(SearchHit::getContent).toList();

        return new PageImpl<>(content, pageable, searchHits.getTotalHits());
    }
}
