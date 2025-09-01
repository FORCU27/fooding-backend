package im.fooding.core.service.store.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
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

    public void save(Long id, String name, StoreCategory category, String address, int reviewCount, double averageRating, int visitCount, String regionId, StoreStatus status, LocalDateTime createdAt) throws IOException {
        StoreDocument storeDocument = StoreDocument.builder()
                .id(id)
                .name(name)
                .category(category.name())
                .address(address)
                .reviewCount(reviewCount)
                .averageRating(averageRating)
                .visitCount(visitCount)
                .regionId(regionId)
                .status(status.name())
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

    public Page<StoreDocument> fullTextSearch(String searchString, StoreSortType sortType, SortDirection direction, List<String> regionIds, StoreCategory category, Pageable pageable) throws IOException {
        SortOrder sortOrder = (direction == SortDirection.ASCENDING) ? SortOrder.Asc : SortOrder.Desc;

        String sortField = switch (sortType) {
            case REVIEW -> "reviewCount";
            case RECENT -> "createdAt";
            case AVERAGE_RATING -> "averageRating";
            case VISIT -> "visitCount";
        };

        // 향후 확장 가능한 검색 필드
        List<String> searchableFields = List.of("name");

        // --- 기본 검색 쿼리 ---
        Query baseQuery = StringUtils.hasText(searchString)
                ? MultiMatchQuery.of(m -> m
                .fields(searchableFields)
                .query(searchString)
                .type(TextQueryType.BestFields)
        )._toQuery()
                : MatchAllQuery.of(m -> m)._toQuery();

        // --- BoolQuery 조립 ---
        BoolQuery.Builder boolBuilder = new BoolQuery.Builder()
                .must(baseQuery);

        // 지역 필터 추가 (있을 경우만)
        if (regionIds != null && !regionIds.isEmpty()) {
            Query regionQuery = TermsQuery.of(t -> t
                    .field("regionId")
                    .terms(v -> v.value(regionIds.stream()
                            .map(FieldValue::of)
                            .toList()))
            )._toQuery();
            boolBuilder.filter(regionQuery);
        }

        // status 필터 추가
        Query statusQuery = TermQuery.of(t -> t
                .field("status")
                .value(StoreStatus.APPROVED.name())
        )._toQuery();
        boolBuilder.filter(statusQuery);

        // 카테고리 필터 추가 (있을 경우만)
        if (category != null) {
            Query categoryQuery = TermQuery.of(t -> t
                    .field("category")
                    .value(category.name())
            )._toQuery();
            boolBuilder.filter(categoryQuery);
        }

        SearchRequest request = SearchRequest.of(s -> s
                .index("stores_v1")
                .query(boolBuilder.build()._toQuery())
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
