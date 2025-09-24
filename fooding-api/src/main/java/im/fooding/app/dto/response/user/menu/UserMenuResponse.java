package im.fooding.app.dto.response.user.menu;

import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.menu.MenuImage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserMenuResponse {

    @Schema(description = "카테고리 ID", example = "1")
    private Long id;

    @Schema(description = "카테고리 이름", example = "식사류")
    private String categoryName;

    @Schema(description = "메뉴 리스트")
    private List<MenuResponse> menu;

    @Builder
    private UserMenuResponse(
            Long id,
            String categoryName,
            List<MenuResponse> menu
    ) {
        this.id = id;
        this.categoryName = categoryName;
        this.menu = menu;
    }

    public static UserMenuResponse of(MenuCategory category, List<Menu> menus, Map<Menu, List<MenuImage>> menuImages) {
        List<MenuResponse> menuResponses = menus.stream().map(
                menu -> MenuResponse.of(menu, menuImages.get(menu))
        ).toList();

        return UserMenuResponse.builder()
                .id(category.getId())
                .categoryName(category.getName())
                .menu(menuResponses)
                .build();
    }
}
