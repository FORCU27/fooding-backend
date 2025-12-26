package im.fooding.realtime.app.repository.waiting;

import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.realtime.app.domain.waiting.StoreWaiting;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DStoreWaitingRepositoryImpl implements DStoreWaitingRepository {

    private final DatabaseClient databaseClient;

    public Mono<Page<StoreWaiting>> listByStoreId(Long storeId, StoreWaitingStatus status, Pageable pageable) {
        String sql = "SELECT * FROM store_waiting WHERE store_id = :storeId AND deleted = false"
                + (status != null ? " AND store_waiting_status = :status" : "")
                + " ORDER BY id ASC LIMIT :limit OFFSET :offset";

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(sql)
                .bind("storeId", storeId)
                .bind("limit", pageable.getPageSize())
                .bind("offset", pageable.getPageNumber() * pageable.getPageSize());

        if (status != null) {
            spec = spec.bind("status", status.name());
        }

        Mono<List<StoreWaiting>> contentMono = spec.map((row, meta) -> StoreWaiting.builder()
                        .id(row.get("id", Long.class))
                        .storeId(row.get("store_id", Long.class))
                        .waitingUserId(row.get("waiting_user_id", Long.class))
                        .user(row.get("user_id", Long.class))
                        .callNumber(row.get("call_number", Integer.class))
                        .callCount(row.get("call_count", Integer.class))
                        .status(StoreWaitingStatus.valueOf(row.get("store_waiting_status", String.class)))
                        .channel(StoreWaitingChannel.valueOf(row.get("channel", String.class)))
                        .infantChairCount(row.get("infant_chair_count", Integer.class))
                        .infantCount(row.get("infant_count", Integer.class))
                        .adultCount(row.get("adult_count", Integer.class))
                        .memo(row.get("memo", String.class))
                        .build())
                .all()
                .collectList(); // Mono<List<StoreWaiting>>

        // 2. 전체 카운트 SQL
        String countSql = "SELECT COUNT(*) FROM store_waiting WHERE store_id = :storeId AND deleted = false"
                + (status != null ? " AND store_waiting_status = :status" : "");

        DatabaseClient.GenericExecuteSpec countSpec = databaseClient.sql(countSql)
                .bind("storeId", storeId);

        if (status != null) {
            countSpec = countSpec.bind("status", status.name());
        }

        Mono<Long> countMono = countSpec.map((row, meta) -> row.get(0, Long.class)).one();

        // 3. content + totalCount 합쳐서 Page 생성
        return Mono.zip(contentMono, countMono)
                .map(tuple -> {
                    List<StoreWaiting> content = tuple.getT1();
                    long total = tuple.getT2();
                    return new PageImpl<>(content, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), total);
                });
    }

    public Mono<Long> count(Long storeId, StoreWaitingStatus status) {
        String baseSql = "SELECT COUNT(*) FROM store_waiting WHERE store_id = :storeId AND deleted = false";
        if (status != null) {
            baseSql += " AND status = :status";
        }

        DatabaseClient.GenericExecuteSpec spec = databaseClient.sql(baseSql)
                .bind("storeId", storeId);
        if (status != null) {
            spec = spec.bind("status", status.name());
        }

        return spec.map((row, meta) -> row.get(0, Long.class)).one();
    }
}
