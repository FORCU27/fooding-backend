package im.fooding.app.dto.response.user.menu;

import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuImage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponse {

    @Schema(description = "메뉴 ID", example = "1")
    private Long id;

    @Schema(description = "메뉴 이름", example = "김치찌개")
    private String name;

    @Schema(description = "메뉴 설명", example = "매운 김치찌개")
    private String description;

    @Schema(description = "메뉴 이미지 URL", example = "['https://example.com/image.jpg', 'https://example.com/image.jpg']")
    private List<String> imageUrls;

    @Schema(description = "메뉴 가격", example = "10000")
    private int price;

    @Schema(description = "메뉴 정렬 순서", example = "1")
    private int sortOrder;

    @Schema(description = "대표 메뉴 여부", example = "true")
    private boolean isSignature;

    @Schema(description = "메뉴 추천 여부", example = "true")
    private boolean isRecommend;

    @Builder
    private MenuResponse(
            Long id,
            String name,
            String description,
            List<String> imageUrls,
            int price,
            int sortOrder,
            boolean isSignature,
            boolean isRecommend
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrls = imageUrls;
        this.price = price;
        this.sortOrder = sortOrder;
        this.isSignature = isSignature;
        this.isRecommend = isRecommend;
    }

    public static MenuResponse of(Menu menu, List<MenuImage> images) {
        List<String> imageUrls = images.stream()
                .map(MenuImage::getImageUrl)
                .toList();

        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .imageUrls(imageUrls)
                .price(menu.getPrice())
                .sortOrder(menu.getSortOrder())
                .isSignature(menu.isSignature())
                .isRecommend(menu.isRecommend())
                .build();
    }
}
