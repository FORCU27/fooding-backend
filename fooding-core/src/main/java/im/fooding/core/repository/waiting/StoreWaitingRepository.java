package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreWaitingRepository extends JpaRepository<StoreWaiting, Long>, QStoreWaitingRepository {
}
