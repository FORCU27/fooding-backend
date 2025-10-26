package im.fooding.core.repository.bookmark;

import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.bookmark.BookmarkSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QBookmarkRepository {
    Page<Bookmark> list(Long storeId, Long userId, BookmarkSortType sortType, String searchString, Pageable pageable);
}
