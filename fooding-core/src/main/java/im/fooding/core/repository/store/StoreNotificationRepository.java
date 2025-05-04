package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreNotificationRepository extends JpaRepository<StoreNotification, Long> {
    Page<StoreNotification> findByStoreIdOrderByCreatedAtDesc(Long storeId, Pageable pageable);

    Page<StoreNotification> findByStoreIdAndCategoryOrderByCreatedAtDesc(Long storeId, String category, Pageable pageable);
}
