package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreServiceRepository extends JpaRepository<StoreService, Long>, QStoreServiceRepository {
}
