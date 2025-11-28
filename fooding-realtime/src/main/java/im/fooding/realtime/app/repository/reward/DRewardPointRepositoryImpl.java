package im.fooding.realtime.app.repository.reward;

import im.fooding.realtime.app.domain.reward.RewardPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class DRewardPointRepositoryImpl implements DRewardPointRepository {
    private final DatabaseClient databaseClient;
    @Override
    public Mono<Page<RewardPoint>> list(
            String searchString,
            Pageable pageable,
            Long storeId,
            String phoneNumber
    ) {
        StringBuilder baseQuery = new StringBuilder("FROM reward_point WHERE deleted=false");

        Map<String, Object> params = new HashMap<>();

        if (storeId != null && storeId != 0) {
            baseQuery.append(" AND store_id = :storeId");
            params.put("storeId", storeId);
        }
        if (phoneNumber != null && phoneNumber.length() == 11 ) {
            baseQuery.append(" AND phone_number = :phoneNumber");
            params.put("phoneNumber", phoneNumber);
        }

        // 🔹 전체 목록용 query
        String dataSql = "SELECT * " + baseQuery +
                " LIMIT :limit OFFSET :offset";

        // limit/offset도 파라미터로 추가
        params.put("limit", pageable.getPageSize());
        params.put("offset", pageable.getOffset());

        // 🔹 count 쿼리
        String countSql = "SELECT COUNT(*) " + baseQuery;

        DatabaseClient.GenericExecuteSpec dataSpec = databaseClient.sql(dataSql);

        for (var entry : params.entrySet()) {
            dataSpec = dataSpec.bind(entry.getKey(), entry.getValue());
        }

        Flux<RewardPoint> dataFlux = dataSpec
                .map((row, metadata) -> {
                    RewardPoint rewardPoint = new RewardPoint();
                    rewardPoint.setId(row.get("id", Long.class));
                    rewardPoint.setStoreId(row.get("store_id", Long.class));
                    rewardPoint.setPhoneNumber(row.get("phone_number", String.class));
                    rewardPoint.setUserId(row.get("user_id", Long.class));
                    rewardPoint.setPoint(row.get("point", Integer.class));
                    rewardPoint.setMemo(row.get("memo", String.class));
                    return rewardPoint;
                })
                .all();
        DatabaseClient.GenericExecuteSpec countSpec = databaseClient.sql(countSql);

        for (var entry : params.entrySet()) {
            if (!entry.getKey().equals("limit") && !entry.getKey().equals("offset")) {
                countSpec = countSpec.bind(entry.getKey(), entry.getValue());
            }
        }

        Mono<Long> countMono = countSpec
                .map((row, metadata) -> {
                    Long t = row.get(0, Long.class);
                    System.out.println( t );
                    return t;
                })
                .one();

        return Mono.zip(dataFlux.collectList(), countMono)
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }
}
