package im.fooding.core.repository.store.recent;

import im.fooding.core.model.store.RecentStore;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecentStoreRepository extends JpaRepository<RecentStore, Long>, QRecentStoreRepository {
    @EntityGraph(attributePaths = {"store", "user"})
    Optional<RecentStore> findByUserIdAndStoreId(long userId, long storeId);
}
