package im.fooding.realtime.app.repository.waiting;

import im.fooding.realtime.app.domain.waiting.StoreWaitingUser;
import reactor.core.publisher.Mono;

public interface DStoreWaitingUserRepository {

    Mono<StoreWaitingUser> findById(long id);
}
