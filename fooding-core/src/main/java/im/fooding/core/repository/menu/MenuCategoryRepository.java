package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.MenuCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    List<MenuCategory> findAllByStoreIdAndDeletedFalse(long storeId);
}
