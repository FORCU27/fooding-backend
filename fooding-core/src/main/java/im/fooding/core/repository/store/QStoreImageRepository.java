package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QStoreImageRepository {

    Optional<StoreImage> findByStore(Long storeId);

    Page<StoreImage> list(long storeId, String searchTag, Pageable pageable);
}
