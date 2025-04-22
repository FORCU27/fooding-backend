package im.fooding.app.service.user.menu;

import im.fooding.app.dto.response.user.menu.UserMenuResponse;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.service.menu.MenuService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMenuService {

    private final MenuService menuService;

    public List<UserMenuResponse> list(Long storeId) {
        return menuService.list(storeId).stream()
                .map(category -> UserMenuResponse.of(category, menuService.list(category)
                ))
                .collect(Collectors.toList());
    }
}
