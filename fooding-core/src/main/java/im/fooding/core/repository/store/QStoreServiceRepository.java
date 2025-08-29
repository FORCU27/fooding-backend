package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.store.StoreServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QStoreServiceRepository {
    Page<StoreService> list( String searchString, Long storeId, StoreServiceType serviceType, Pageable pageable );
}
