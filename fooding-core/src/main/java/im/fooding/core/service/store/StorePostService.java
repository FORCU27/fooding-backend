package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostSortType;
import im.fooding.core.repository.store.StorePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorePostService {
    private final StorePostRepository storePostRepository;

    public List<StorePost> list(Long storeId, Boolean isActive) {
        // Backward-compatible non-paged list (CEO/User use case)
        return storePostRepository.list(storeId, isActive, StorePostSortType.RECENT, null, Pageable.unpaged()).getContent();
    }

    public Page<StorePost> list(Long storeId, Boolean isActive, StorePostSortType sortType, String searchString, Pageable pageable) {
        return storePostRepository.list(storeId, isActive, sortType, searchString, pageable);
    }

    public StorePost findById(Long id) {
        return storePostRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_POST_NOT_FOUND));
    }

    public StorePost create(StorePost storePost) {
        return storePostRepository.save(storePost);
    }

    public void update(StorePost storePost, String title, String content, List<String> tags, boolean isFixed, boolean isNotice, boolean isCommentAvailable) {
        storePost.update(title, content, tags, isFixed, isNotice, isCommentAvailable);
    }

    public void delete(StorePost storePost, Long deletedBy) {
        storePost.delete(deletedBy);
    }

    public void increaseLikeCount(StorePost storePost) {
        storePost.increaseLikeCount();
    }

    public void decreaseLikeCount(StorePost storePost) {
        storePost.decreaseLikeCount();
    }

    public void increaseCommentCount(StorePost storePost) {
        storePost.increaseCommentCount();
    }

    public void decreaseCommentCount(StorePost storePost) {
        storePost.decreaseCommentCount();
    }

    public void increaseViewCount(StorePost storePost) {
        storePost.increaseViewCount();
    }

    public void decreaseViewCount(StorePost storePost) {
        storePost.decreaseViewCount();
    }

    public void active(StorePost storePost) {
        storePost.active();
    }

    public void inactive(StorePost storePost) {
        storePost.inactive();
    }
}
