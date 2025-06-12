package im.fooding.app.dto.response.admin.menu;

import im.fooding.core.model.menu.MenuCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminMenuCategoryResponse {

    @Schema(description = "메뉴 카테고리 ID", example = "1")
    Long id;

    @Schema(description = "가게 ID", example = "1")
    Long storeId;

    @Schema(description = "메뉴 카테고리 이름", example = "식사류")
    String name;

    @Schema(description = "메뉴 카테고리 설명", example = "시그니처 대표메뉴!")
    String description;

    @Schema(description = "카테고리 정렬", example = "1")
    Integer sortOrder;

    public static AdminMenuCategoryResponse from(MenuCategory menuCategory) {
        return AdminMenuCategoryResponse.builder()
                .id(menuCategory.getId())
                .storeId(menuCategory.getStore().getId())
                .name(menuCategory.getName())
                .description(menuCategory.getDescription())
                .sortOrder(menuCategory.getSortOrder())
                .build();
    }
}
