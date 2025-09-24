package im.fooding.app.dto.request.admin.menu;

import im.fooding.core.dto.request.menu.MenuUpdateRequest;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record AdminMenuUpdateRequest(

        @Schema(description = "가게 ID")
        @NotNull
        Long storeId,

        @Schema(description = "메뉴 카테고리 ID")
        @NotNull
        Long categoryId,

        @Schema(description = "메뉴 이름")
        @NotNull
        String name,

        @Schema(description = "메뉴 가격")
        @PositiveOrZero
        int price,

        @Schema(description = "메뉴 설명")
        @NotNull
        String description,

        @Schema(description = "메뉴 사진")
        @NotNull
        List<String> imageIds,

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

    public MenuUpdateRequest toMenuUpdateRequest(long id, Store store, MenuCategory category) {
        return MenuUpdateRequest.builder()
                .store(store)
                .id(id)
                .category(category)
                .name(name)
                .price(price)
                .description(description)
                .sortOrder(sortOrder)
                .isSignature(isSignature)
                .isRecommend(isRecommend)
                .build();
    }
}
