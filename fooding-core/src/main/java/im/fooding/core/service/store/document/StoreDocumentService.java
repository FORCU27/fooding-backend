package im.fooding.core.service.store.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import im.fooding.core.global.util.ElasticsearchUtil;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreDocumentService {
    private final ElasticsearchClient client;

    public void save(StoreDocument storeDocument) throws IOException {
        client.index(i -> i
                .index(ElasticsearchUtil.getIndexName(storeDocument.getClass()))       // 인덱스 이름
                .id(String.valueOf(storeDocument.getId()))      // 도큐먼트 ID (선택 사항, 없으면 자동 생성)
                .document(storeDocument)        // 저장할 객체
        );
    }

    public void delete(StoreDocument storeDocument) throws IOException {
        client.delete(d -> d
                .index(ElasticsearchUtil.getIndexName(storeDocument.getClass())) // 인덱스 이름
                .id(String.valueOf(storeDocument.getId())) // 삭제할 문서의 ID
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

        SearchResponse<StoreDocument> response = client.search(request, StoreDocument.class);

        List<StoreDocument> content = response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long totalHits = response.hits().total() != null ? response.hits().total().value() : 0;

        return new PageImpl<>(content, pageable, totalHits);
    }
}
