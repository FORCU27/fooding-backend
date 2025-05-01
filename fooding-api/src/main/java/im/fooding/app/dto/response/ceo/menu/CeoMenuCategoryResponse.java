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

    @Schema(description = "메뉴 카테고리 정렬 순서", example = "1")
    private int sortOrder;

    @Builder
    private CeoMenuCategoryResponse(Long id, String name, int sortOrder) {
        this.id = id;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public static CeoMenuCategoryResponse of(MenuCategory menuCategory) {
        return CeoMenuCategoryResponse.builder()
                .id(menuCategory.getId())
                .name(menuCategory.getName())
                .sortOrder(menuCategory.getSortOrder())
                .build();
    }
}
