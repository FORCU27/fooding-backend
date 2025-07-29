package im.fooding.core.repository.bookmark;

import im.fooding.core.model.bookmark.Bookmark;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, QBookmarkRepository {
    Optional<Bookmark> findByStoreIdAndUserIdAndDeletedIsFalse(long storeId, long userId);

    @EntityGraph(attributePaths = {"store", "user"})
    List<Bookmark> findAllByUserIdAndDeletedIsFalse(long userId);

    boolean existsByStoreIdAndUserIdAndDeletedIsFalse(Long storeId, Long userId);
}
