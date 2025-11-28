package im.fooding.realtime.app.repository.reward;

import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import im.fooding.realtime.app.domain.reward.RewardLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class DRewardLogRepositoryImpl implements DRewardLogRepository{
    private final DatabaseClient databaseClient;

    @Override
    public Mono<Page<RewardLog>> list(
            String searchString,
            Pageable pageable,
            Long storeId,
            String phoneNumber,
            RewardStatus status
    ){
        // SQL builder
        StringBuilder baseQuery = new StringBuilder("FROM reward_log WHERE deleted=false");

        // 파라미터 저장용
        Map<String, Object> params = new HashMap<>();

        if (storeId != null && storeId != 0) {
            baseQuery.append(" AND store_id = :storeId");
            params.put("storeId", storeId);
        }
        if (phoneNumber != null && phoneNumber.length() == 11 ) {
            baseQuery.append(" AND phone_number = :phoneNumber");
            params.put("phoneNumber", phoneNumber);
        }
        if (status != null) {
            baseQuery.append(" AND status = :status");
            params.put("status", status.name());
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

        Flux<RewardLog> dataFlux = dataSpec
                .map((row, metadata) -> {
                    RewardLog rewardLog = new RewardLog();
                    rewardLog.setId(row.get("id", Long.class));
                    rewardLog.setStoreId(row.get("store_id", Long.class));
                    rewardLog.setPhoneNumber(row.get("phone_number", String.class));
                    rewardLog.setPoint(row.get("point", Integer.class));

                    String statusString = row.get( "status", String.class );
                    rewardLog.setStatus(RewardStatus.valueOf( statusString ));

                    String typeString = row.get( "type", String.class );
                    rewardLog.setType(RewardType.valueOf(typeString));

                    String channelString = row.get("channel", String.class );
                    rewardLog.setChannel(RewardChannel.valueOf(channelString));
                    rewardLog.setMemo(row.get("memo", String.class));
                    return rewardLog;
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

    @Override
    public Mono<Long> listCount(String searchString, Long storeId, String phoneNumber, RewardStatus status) {
        // SQL builder
        StringBuilder baseQuery = new StringBuilder("FROM reward_log WHERE deleted=false");

        // 파라미터 저장용
        Map<String, Object> params = new HashMap<>();

        if (storeId != null && storeId != 0) {
            baseQuery.append(" AND store_id = :storeId");
            params.put("storeId", storeId);
        }
        if (phoneNumber != null && phoneNumber.length() == 11 ) {
            baseQuery.append(" AND phone_number = :phoneNumber");
            params.put("phoneNumber", phoneNumber);
        }
        if (status != null) {
            baseQuery.append(" AND status = :status");
            params.put("status", status.name() );
        }

        // 🔹 count 쿼리
        String countSql = "SELECT COUNT(*) " + baseQuery;

        DatabaseClient.GenericExecuteSpec countSpec = databaseClient.sql(countSql);

        return countSpec
                .map((row, metadata) -> {
                    Long t = row.get(0, Long.class);
                    System.out.println( t );
                    return t;
                })
                .one();
    }
}
