package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QStorePostRepository {
    Page<StorePost> list(Long storeId, Boolean isActive, StorePostSortType sortType, String searchString, Pageable pageable);
}

