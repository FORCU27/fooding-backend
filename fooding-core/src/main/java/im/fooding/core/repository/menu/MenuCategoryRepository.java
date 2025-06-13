package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.MenuCategory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long>, QMenuCategoryRepository {

    List<MenuCategory> findAllByStoreIdAndDeletedFalse(long storeId);

    Page<MenuCategory> findAllByDeletedFalse(Pageable pageable);
}
