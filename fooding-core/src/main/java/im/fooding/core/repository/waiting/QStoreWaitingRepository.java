package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QStoreWaitingRepository {

    Page<StoreWaiting> findAllByStoreIdAndStatus(long storeId, StoreWaitingStatus status, Pageable pageable);
}
