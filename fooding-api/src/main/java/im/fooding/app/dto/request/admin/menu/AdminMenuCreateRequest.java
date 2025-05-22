package im.fooding.app.dto.request.admin.menu;

import im.fooding.core.dto.request.menu.MenuCreateRequest;
import im.fooding.core.model.menu.MenuCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AdminMenuCreateRequest(

        @Schema(description = "메뉴 카테고리 ID")
        @NotNull
        Long categoryId,

        @Schema(description = "메뉴 이름")
        @NotNull
        String name,

        @Schema(description = "메뉴 가격")
        @NotNull
        BigDecimal price,

        @Schema(description = "메뉴 설명")
        @NotNull
        String description,

        @Schema(description = "메뉴 사진")
        @NotNull
        String imageUrl,

        @Schema(description = "카테고리 정렬")
        @NotNull
        int sortOrder,

        @Schema(description = "대표 메뉴 여부")
        @NotNull
        Boolean isSignature,

        @Schema(description = "메뉴 추천 여부")
        @NotNull
        Boolean isRecommend
) {

    public MenuCreateRequest toMenuCreateRequest(MenuCategory category) {
        return MenuCreateRequest.builder()
                .category(category)
                .name(name)
                .price(price)
                .description(description)
                .imageUrl(imageUrl)
                .sortOrder(sortOrder)
                .isSignature(isSignature)
                .isRecommend(isRecommend)
                .build();
    }
}
