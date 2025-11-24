package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePostCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorePostCommentLikeRepository extends JpaRepository<StorePostCommentLike, Long> {
    StorePostCommentLike findByStorePostCommentIdAndUserIdAndDeletedIsFalse(Long storePostCommentId, Long userId);

    List<StorePostCommentLike> findByUserIdAndDeletedIsFalse(Long userId);
}
