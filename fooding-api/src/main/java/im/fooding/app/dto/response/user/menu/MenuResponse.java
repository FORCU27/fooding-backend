package im.fooding.app.dto.response.user.menu;

import im.fooding.core.model.menu.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "메뉴 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

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
            String imageUrl,
            int price,
            int sortOrder,
            boolean isSignature,
            boolean isRecommend
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.sortOrder = sortOrder;
        this.isSignature = isSignature;
        this.isRecommend = isRecommend;
    }

    public static MenuResponse of(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .imageUrl(menu.getImageUrl())
                .price(menu.getPrice().intValue())
                .sortOrder(menu.getSortOrder())
                .isSignature(menu.isSignature())
                .isRecommend(menu.isRecommend())
                .build();
    }
}
