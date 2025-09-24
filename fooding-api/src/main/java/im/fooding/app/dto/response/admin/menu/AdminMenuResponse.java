package im.fooding.app.dto.response.admin.menu;

import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuImage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record AdminMenuResponse(

        @Schema(description = "ID")
        long id,

        @Schema(description = "가게 ID")
        long storeId,

        @Schema(description = "메뉴 카테고리 ID")
        long categoryId,

        @Schema(description = "메뉴 이름")
        String name,

        @Schema(description = "메뉴 가격")
        int price,

        @Schema(description = "메뉴 설명")
        String description,

        @Schema(description = "메뉴 사진")
        List<String> imageUrls,

        @Schema(description = "카테고리 정렬")
        int sortOrder,

        @Schema(description = "대표 메뉴 여부")
        Boolean isSignature,

        @Schema(description = "메뉴 추천 여부")
        Boolean isRecommend
) {

    public static AdminMenuResponse of(Menu menu, List<MenuImage> images) {
        List<String> imageUrls = images.stream().map(MenuImage::getImageUrl)
                .toList();

        return AdminMenuResponse.builder()
                .id(menu.getId())
                .storeId(menu.getStore().getId())
                .categoryId(menu.getCategory().getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .imageUrls(imageUrls)
                .sortOrder(menu.getSortOrder())
                .isSignature(menu.isSignature())
                .isRecommend(menu.isRecommend())
                .build();
    }
}
