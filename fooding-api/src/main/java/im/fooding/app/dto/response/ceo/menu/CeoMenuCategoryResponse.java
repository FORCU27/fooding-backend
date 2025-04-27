package im.fooding.app.dto.response.ceo.menu;

import im.fooding.core.model.menu.MenuCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoMenuCategoryResponse {

    @Schema(description = "메뉴 카테고리 ID", example = "1")
    private Long id;

    @Schema(description = "메뉴 카테고리 이름", example = "식사류")
    private String name;

    @Builder
    private CeoMenuCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CeoMenuCategoryResponse of(MenuCategory menuCategory) {
        return CeoMenuCategoryResponse.builder()
                .id(menuCategory.getId())
                .name(menuCategory.getName())
                .build();
    }
}
