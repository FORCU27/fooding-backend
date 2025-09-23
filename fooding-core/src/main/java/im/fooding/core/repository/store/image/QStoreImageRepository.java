package im.fooding.core.repository.store.image;

import im.fooding.core.model.store.StoreImage;
import im.fooding.core.model.store.StoreImageSortType;
import im.fooding.core.model.store.StoreImageTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QStoreImageRepository {

    Optional<StoreImage> findByStore(Long storeId);

    Page<StoreImage> list(long storeId, StoreImageTag tag, Boolean isMain, StoreImageSortType sortType, Pageable pageable);
}
