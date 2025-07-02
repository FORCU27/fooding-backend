package im.fooding.core.service.bookmark;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.bookmark.Bookmark;
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

    public void delete(long storeId, long userId) {
        Bookmark bookmark = repository.findByStoreIdAndUserIdAndDeletedIsFalse(storeId, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_BOOKMARK_NOT_FOUND));
        bookmark.delete(userId);
    }

    public Page<Bookmark> list(Long storeId, Long userId, String searchString, Pageable pageable) {
        return repository.list(storeId, userId, searchString, pageable);
    }

    public List<Bookmark> findAllByUserId(Long userId) {
        return repository.findAllByUserIdAndDeletedIsFalse(userId);
    }
}
