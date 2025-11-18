package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorePostLikeRepository extends JpaRepository<StorePostLike, Long> {
    StorePostLike findByStorePostIdAndUserIdAndDeletedIsFalse(Long storePostId, Long userId);

    List<StorePostLike> findByUserIdAndDeletedIsFalse(Long userId);
}
