package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StorePostComment;
import im.fooding.core.model.store.StorePostCommentLike;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.store.StorePostCommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorePostCommentLikeService {
    private final StorePostCommentLikeRepository repository;

    public void create(StorePostComment storePostComment, User user) {
        StorePostCommentLike storePostCommentLike = findByStorePostCommentIdAndUserId(storePostComment.getId(), user.getId());
        if (storePostCommentLike != null) {
            throw new ApiException(ErrorCode.STORE_POST_COMMENT_LIKE_EXIST);
        }

        repository.save(StorePostCommentLike.builder()
                .storePostComment(storePostComment)
                .user(user)
                .build()
        );
    }

    public void delete(StorePostComment storePostComment, User user) {
        StorePostCommentLike storePostCommentLike = findByStorePostCommentIdAndUserId(storePostComment.getId(), user.getId());
        if (storePostCommentLike == null) {
            throw new ApiException(ErrorCode.STORE_POST_COMMENT_LIKE_NOT_FOUND);
        }
        storePostCommentLike.delete(user.getId());
    }

    public StorePostCommentLike findByStorePostCommentIdAndUserId(Long storePostCommentId, Long userId) {
        return repository.findByStorePostCommentIdAndUserIdAndDeletedIsFalse(storePostCommentId, userId);
    }

    public List<StorePostCommentLike> findByUserId(Long userId) {
        return repository.findByUserIdAndDeletedIsFalse(userId);
    }
}
