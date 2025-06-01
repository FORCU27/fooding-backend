package im.fooding.app.dto.request.admin.menu;

import im.fooding.core.dto.request.menu.MenuCreateRequest;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AdminMenuCreateRequest(

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
        @NotNull
        BigDecimal price,

        @Schema(description = "메뉴 설명")
        @NotNull
        String description,

        @Schema(description = "이미지 업로드 후 받은 id", example = "819f4bca-2739-46ca-9156-332c86eda619")
        @NotNull
        String imageId,

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

    public MenuCreateRequest toMenuCreateRequest(Store store, MenuCategory category, String imageUrl) {
        return MenuCreateRequest.builder()
                .store(store)
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
