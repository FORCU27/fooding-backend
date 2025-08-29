package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QStoreServiceRepository {
    Page<StoreService> list( String searchString, Long storeId, Pageable pageable );
}
