package im.fooding.realtime.app.repository.waiting;

import im.fooding.realtime.app.domain.waiting.StoreWaitingUser;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DStoreWaitingUserRepositoryImpl implements DStoreWaitingUserRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<StoreWaitingUser> findById(long id) {
        String sql = "SELECT * FROM waiting_user WHERE id = :id AND deleted = false";

        return databaseClient.sql(sql)
                .bind("id", id)
                .map((row, meta) -> StoreWaitingUser.builder()
                        .id(row.get("id", Long.class))
                        .storeId(row.get("store_id", Long.class))
                        .name(row.get("name", String.class))
                        .phoneNumber(row.get("phone_number", String.class))
                        .termsAgreed(Boolean.TRUE.equals(row.get("terms_agreed", Boolean.class)))
                        .privacyPolicyAgreed(Boolean.TRUE.equals(row.get("privacy_policy_agreed", Boolean.class)))
                        .thirdPartyAgreed(Boolean.TRUE.equals(row.get("third_party_agreed", Boolean.class)))
                        .marketingConsent(Boolean.TRUE.equals(row.get("marketing_consent", Boolean.class)))
                        .count(row.get("count", Integer.class))
                        .build())
                .one();
    }
}
