package im.fooding.app.service.user.menu;

import im.fooding.app.dto.response.user.menu.UserMenuResponse;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.menu.MenuImage;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.menu.MenuImageService;
import im.fooding.core.service.menu.MenuService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMenuService {

    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;
    private final MenuImageService menuImageService;

    public List<UserMenuResponse> list(Long storeId) {
        List<MenuCategory> menuCategoryList = menuCategoryService.list(storeId);

        Map<Long, List<Menu>> menuList = menuService
                .list(menuCategoryList
                        .stream()
                        .map(MenuCategory::getId)
                        .toList()
                )
                .stream()
                .collect(Collectors.groupingBy(menu -> menu.getCategory().getId()));
        Map<Menu, List<MenuImage>> menuImages = new HashMap<>();
        for (List<Menu> menus : menuList.values()) {
            for (Menu menu : menus) {
                List<MenuImage> images = menuImageService.listByMenuId(menu.getId());
                menuImages.put(menu, images);
            }
        }

        return menuCategoryList.stream()
                .map(category -> UserMenuResponse.of(
                        category,
                        menuList.getOrDefault(category.getId(), List.of()),
                        menuImages
                ))
                .toList();
    }
}
