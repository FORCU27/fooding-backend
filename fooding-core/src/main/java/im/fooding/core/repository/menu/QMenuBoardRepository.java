package im.fooding.core.repository.menu;

import im.fooding.core.model.menu.MenuBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QMenuBoardRepository {

    Page<MenuBoard> listByStoreId(long storeId, Pageable pageable);

    long countByStoreId(long storeId);
}
