package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QStorePostCommentRepository {
    Page<StorePostComment> list(Long storePostId, String searchText, Pageable pageable);
}
