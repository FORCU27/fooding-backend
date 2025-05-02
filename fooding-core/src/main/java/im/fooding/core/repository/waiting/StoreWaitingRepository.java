package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreWaitingRepository extends JpaRepository<StoreWaiting, Long>, QStoreWaitingRepository {

    long countByStatusAndCreatedAtBeforeAndDeletedFalse(StoreWaitingStatus status, LocalDateTime createdAt);

    boolean existsByStoreAndStatusAndDeletedFalse(Store store, StoreWaitingStatus status);
}
