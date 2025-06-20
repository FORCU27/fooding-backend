package im.fooding.core.repository.store.information;

import im.fooding.core.model.store.information.StoreOperatingHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreOperatingHourRepository extends JpaRepository<StoreOperatingHour, Long>, QStoreOperatingHourRepository {
    Optional<StoreOperatingHour> findByStoreIdAndDeletedIsFalse(long storeId);

    boolean existsByStoreIdAndDeletedIsFalse(long storeId);
}
