package im.fooding.realtime.app.repository.waiting;

import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.realtime.app.domain.waiting.StoreWaiting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DStoreWaitingRepository {

    Mono<Page<StoreWaiting>> listByStoreId(Long storeId, StoreWaitingStatus status, Pageable pageable);
}
