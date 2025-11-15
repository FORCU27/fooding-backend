package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostLike;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.store.StorePostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorePostLikeService {
    private final StorePostLikeRepository repository;

    public void create(StorePost storePost, User user) {
        StorePostLike storePostLike = findByStorePostIdAndUserId(storePost.getId(), user.getId());
        if  (storePostLike != null) {
            throw new ApiException(ErrorCode.STORE_POST_LIKE_EXIST);
        }

        repository.save(StorePostLike.builder()
                .storePost(storePost)
                .user(user)
                .build()
        );
    }

    public void delete(StorePost storePost, User user) {
        StorePostLike storePostLike = findByStorePostIdAndUserId(storePost.getId(), user.getId());
        if (storePostLike == null) {
            throw new ApiException(ErrorCode.STORE_POST_LIKE_NOT_FOUND);
        }
        storePostLike.delete(user.getId());
    }

    public StorePostLike findByStorePostIdAndUserId(Long storePostId, Long userId) {
        return repository.findByStorePostIdAndUserIdAndDeletedIsFalse(storePostId, userId);
    }

    public List<StorePostLike> findByUserId(Long userId) {
        return repository.findByUserIdAndDeletedIsFalse(userId);
    }
}
