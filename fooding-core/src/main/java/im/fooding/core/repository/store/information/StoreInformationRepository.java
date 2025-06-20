package im.fooding.core.repository.store.information;

import im.fooding.core.model.store.information.StoreInformation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreInformationRepository extends JpaRepository<StoreInformation, Long> {
    @Override
    @EntityGraph(attributePaths = {"store"})
    Optional<StoreInformation> findById(Long id);

    boolean existsByStoreIdAndDeletedIsFalse(long storeId);

    Optional<StoreInformation> findByStoreIdAndDeletedIsFalse(long storeId);
}
