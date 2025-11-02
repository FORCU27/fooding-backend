package im.fooding.core.service.bookmark;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.bookmark.BookmarkSortType;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.bookmark.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {
    private final BookmarkRepository repository;

    public Bookmark create(Store store, User user) {
        repository.findByStoreIdAndUserIdAndDeletedIsFalse(store.getId(), user.getId()).ifPresent(bookmark -> {
            throw new ApiException(ErrorCode.STORE_BOOKMARK_EXIST);
        });

        Bookmark bookmark = Bookmark.builder()
                .store(store)
                .user(user)
                .build();

        return repository.save(bookmark);
    }

    public void delete(Bookmark bookmark, long deletedBy) {
        bookmark.delete(deletedBy);
    }

    public Page<Bookmark> list(Long storeId, Long userId, BookmarkSortType sortType, String searchString, Pageable pageable) {
        return repository.list(storeId, userId, sortType, searchString, pageable);
    }

    public List<Bookmark> findAllByUserId(Long userId) {
        return repository.findAllByUserIdAndDeletedIsFalse(userId);
    }

    public void increaseVerifiedCount(Bookmark bookmark) {
        bookmark.increaseVerifiedCount();
    }

    public Bookmark findById(long id) {
        return repository.findById(id).filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_BOOKMARK_NOT_FOUND));
    }

    public Bookmark findByStoreIdAndUserId(Long storeId, Long userId) {
        return repository.findByStoreIdAndUserIdAndDeletedIsFalse(storeId, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_BOOKMARK_NOT_FOUND));
    }

    public boolean existsByStoreIdAndUserId(Long storeId, Long userId) {
        return repository.existsByStoreIdAndUserIdAndDeletedIsFalse(storeId, userId);
    }

    public void updateStarred(long id, boolean isStarred) {
        Bookmark bookmark = findById(id);
        bookmark.updateStarred(isStarred);
    }
}
