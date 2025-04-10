package im.fooding.core.repository.store;

import im.fooding.core.model.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
