package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QStorePostRepository {
    Page<StorePost> list(Long storeId, String searchString, Pageable pageable);
}

