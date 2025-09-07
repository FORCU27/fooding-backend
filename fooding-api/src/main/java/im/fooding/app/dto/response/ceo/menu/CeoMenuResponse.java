package im.fooding.app.dto.response.ceo.menu;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import im.fooding.core.model.menu.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CeoMenuResponse {

    @Schema(description = "ID", requiredMode = REQUIRED, example = "1")
    long id;

    @Schema(description = "가게 ID", requiredMode = REQUIRED, example = "1")
    long storeId;

    @Schema(description = "메뉴 카테고리 ID", requiredMode = REQUIRED, example = "1")
    long categoryId;

    @Schema(description = "메뉴 이름", requiredMode = REQUIRED, example = "초밥")
    String name;

    @Schema(description = "메뉴 가격", requiredMode = REQUIRED, example = "12000")
    BigDecimal price;

    @Schema(description = "메뉴 설명", requiredMode = REQUIRED, example = "맛있는 초밥")
    String description;

    @Schema(description = "메뉴 사진", requiredMode = REQUIRED, example = "https://d27gz6v6wvae1d.cloudfront.net/fooding/gigs/...")
    String imageUrl;

    @Schema(description = "카테고리 정렬", requiredMode = REQUIRED, example = "1")
    int sortOrder;

    @Schema(description = "대표 메뉴 여부", requiredMode = REQUIRED, example = "true")
    Boolean isSignature;

    @Schema(description = "메뉴 추천 여부", requiredMode = REQUIRED, example = "true")
    Boolean isRecommend;

    public static CeoMenuResponse from(Menu menu) {
        return CeoMenuResponse.builder()
                .id(menu.getId())
                .storeId(menu.getStore().getId())
                .categoryId(menu.getCategory().getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .imageUrl(menu.getImageUrl())
                .sortOrder(menu.getSortOrder())
                .isSignature(menu.isSignature())
                .isRecommend(menu.isRecommend())
                .build();
    }
}
