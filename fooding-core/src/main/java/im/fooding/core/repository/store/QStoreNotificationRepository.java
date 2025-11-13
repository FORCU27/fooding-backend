package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface QStoreNotificationRepository {

    Page<StoreNotification> list(Pageable pageable);
}
