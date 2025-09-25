package im.fooding.core.repository.store.recent;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface QRecentStoreRepository {
    Page<Store> findRecentStores(long userId, Set<StoreStatus> statuses, Pageable pageable);
}
