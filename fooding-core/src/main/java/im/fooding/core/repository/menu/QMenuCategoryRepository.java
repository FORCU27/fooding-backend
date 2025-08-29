package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QMenuCategoryRepository {

    int getMaxSortOrder(Long storeId);

    Page<MenuCategory> list(Long storeId, String searchString, Pageable pageable);
}
