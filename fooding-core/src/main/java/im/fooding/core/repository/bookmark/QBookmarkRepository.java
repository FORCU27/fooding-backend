package im.fooding.core.repository.bookmark;

import im.fooding.core.model.bookmark.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QBookmarkRepository {
    Page<Bookmark> list(Long storeId, Long userId, String searchString, Pageable pageable);
}
