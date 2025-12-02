package im.fooding.realtime.app.repository.waiting;

import im.fooding.realtime.app.domain.waiting.StoreWaiting;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreWaitingRepository extends R2dbcRepository<StoreWaiting, Long>, DStoreWaitingRepository {
}
