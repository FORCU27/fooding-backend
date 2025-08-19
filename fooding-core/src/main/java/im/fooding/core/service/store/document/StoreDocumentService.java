package im.fooding.core.service.store.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.document.StoreDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreDocumentService {
    private final ElasticsearchClient client;
    private final ObjectMapper objectMapper;

    public void save(Long id, String name, String category, String address, int reviewCount, double averageRating, int visitCount, LocalDateTime createdAt) throws IOException {
        StoreDocument storeDocument = StoreDocument.builder()
                .id(id)
                .name(name)
                .category(category)
                .address(address)
                .reviewCount(reviewCount)
                .averageRating(averageRating)
                .visitCount(visitCount)
                .createdAt(createdAt)
                .build();

        client.index(i -> i
                .index(storeDocument.getIndex())
                .id(storeDocument.getId().toString())
                .document(storeDocument)
        );
    }

    public void delete(Long id) throws IOException {
        StoreDocument storeDocument = StoreDocument.builder()
                .id(id)
                .build();

        client.delete(d -> d
                .index(storeDocument.getIndex())
                .id(storeDocument.getId().toString())
        );
    }

    public Page<StoreDocument> fullTextSearch(String searchString, StoreSortType sortType, SortDirection direction, Pageable pageable) throws IOException {
        SortOrder sortOrder = (direction == SortDirection.ASCENDING) ? SortOrder.Asc : SortOrder.Desc;

        String sortField = switch (sortType) {
            case REVIEW -> "reviewCount";
            case RECENT -> "createdAt";
            case AVERAGE_RATING -> "averageRating";
            case VISIT -> "visitCount";
        };

        // 향후 확장될 수 있는 검색 필드 리스트
        List<String> searchableFields = List.of("name");

        Query query;
        if (!StringUtils.hasText(searchString)) {
            query = MatchAllQuery.of(m -> m)._toQuery();
        } else {
            query = MultiMatchQuery.of(m -> m
                    .fields(searchableFields)
                    .query(searchString)
                    .type(TextQueryType.BestFields) // 또는 most_fields, cross_fields 등 상황에 따라 조절 가능
            )._toQuery();
        }

        SearchRequest request = SearchRequest.of(s -> s
                .index("stores_v1")
                .query(query)
                .sort(sort -> sort
                        .field(f -> f
                                .field(sortField)
                                .order(sortOrder)
                        )
                )
                .from((int) pageable.getOffset())
                .size(pageable.getPageSize())
        );

        SearchResponse<Map> response = client.search(request, Map.class);
        List<StoreDocument> contents = response.hits().hits().stream()
                .map(Hit::source)
                .map(sourceMap -> objectMapper.convertValue(sourceMap, StoreDocument.class))
                .collect(Collectors.toList());

        long totalHits = response.hits().total() != null ? response.hits().total().value() : 0;

        return new PageImpl<>(contents, pageable, totalHits);
    }
}
