package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostComment;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.store.StorePostCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorePostCommentService {
    private final StorePostCommentRepository repository;

    public StorePostComment create(StorePost storePost, StorePostComment parent, User writer, String content, boolean isOwner) {
        StorePostComment storePostComment = StorePostComment.builder()
                .storePost(storePost)
                .writer(writer)
                .parent(parent)
                .content(content)
                .isOwner(isOwner)
                .build();
        return repository.save(storePostComment);
    }

    public void update(long id, User writer, String content) {
        StorePostComment storePostComment = findById(id);
        storePostComment.update(writer, content);
    }

    public StorePostComment findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_POST_COMMENT_NOT_FOUND));
    }

    public void delete(StorePostComment storePostComment, User writer, Long deletedBy) {
        storePostComment.delete(writer, deletedBy);
    }

    public void increaseLikeCount(StorePostComment storePostComment) {
        storePostComment.increaseLikeCount();
    }

    public void decreaseLikeCount(StorePostComment storePostComment) {
        storePostComment.decreaseLikeCount();
    }

    public Page<StorePostComment> list(Long storePostId, String searchString, Pageable pageable) {
        return repository.list(storePostId, searchString, pageable);
    }
}
