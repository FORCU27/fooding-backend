package im.fooding.app.service.user.menu;

import im.fooding.app.dto.response.user.menu.UserMenuResponse;
import im.fooding.core.service.menu.MenuCategoryService;
import im.fooding.core.service.menu.MenuService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMenuService {

    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;

    public List<UserMenuResponse> list(Long storeId) {
        return menuCategoryService.list(storeId).stream()
                .map(category -> UserMenuResponse.of(category, menuService.list(category)
                ))
                .collect(Collectors.toList());
    }
}
