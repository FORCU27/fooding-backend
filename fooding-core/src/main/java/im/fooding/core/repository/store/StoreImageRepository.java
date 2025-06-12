package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long>, QStoreImageRepository {
    List<StoreImage> findAllByOrderBySortOrderAsc();
}
