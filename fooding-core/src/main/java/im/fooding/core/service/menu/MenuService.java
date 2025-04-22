package im.fooding.core.service.menu;

import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.repository.menu.MenuCategoryRepository;
import im.fooding.core.repository.menu.MenuRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MenuService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;

    /**
     * 메뉴 목록 조회
     *
     * @param menuCategory
     * @return List<Menu>
     */
    public List<Menu> list(MenuCategory menuCategory) {
        return menuRepository.list(menuCategory);
    }

    /**
     * 메뉴 카테고리 목록 조회
     *
     * @param storeId
     * @return List<MenuCategory>
     */
    public List<MenuCategory> list(long storeId) {
        return menuCategoryRepository.findAllByStoreId(storeId);
    }
}
