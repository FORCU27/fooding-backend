package im.fooding.app.dto.request.ceo.menu;

import im.fooding.core.dto.request.menu.MenuUpdateRequest;
import im.fooding.core.model.menu.MenuCategory;
import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class CeoMenuUpdateRequest {

    @Schema(description = "가게 ID", example = "1")
    @NotNull
    Long storeId;

    @Schema(description = "메뉴 카테고리 ID", example = "1")
    @NotNull
    Long categoryId;

    @Schema(description = "메뉴 이름", example = "초밥")
    @NotNull
    String name;

    @Schema(description = "메뉴 가격", example = "12000")
    @PositiveOrZero
    int price;

    @Schema(description = "메뉴 설명", example = "맛있는 초밥")
    @NotNull
    String description;

    @Schema(description = "이미지 업로드 후 받은 id", example = "819f4bca-2739-46ca-9156-332c86eda619")
    @NotNull
    String imageId;

    @Schema(description = "카테고리 정렬", example = "1")
    @NotNull
    int sortOrder;

    @Schema(description = "대표 메뉴 여부", example = "true")
    @NotNull
    Boolean isSignature;

    @Schema(description = "메뉴 추천 여부", example = "true")
    @NotNull
    Boolean isRecommend;

    public MenuUpdateRequest toMenuUpdateRequest(long id, Store store, MenuCategory category, String imageUrl) {
        return MenuUpdateRequest.builder()
                .store(store)
                .id(id)
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
