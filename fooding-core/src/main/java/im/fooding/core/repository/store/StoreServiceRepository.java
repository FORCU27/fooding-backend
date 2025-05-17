package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreServiceRepository extends JpaRepository<StoreService, Long>, QStoreServiceRepository {
    List<StoreService> findByStoreId( Long storeId );
}
