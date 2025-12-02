package im.fooding.realtime.app.repository.waiting;

import im.fooding.realtime.app.domain.waiting.StoreWaitingUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface StoreWaitingUserRepository extends R2dbcRepository<StoreWaitingUser, Long>, DStoreWaitingUserRepository {
}
