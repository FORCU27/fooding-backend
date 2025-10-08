package im.fooding.app.dto.response.user.reward;

import im.fooding.app.dto.response.file.FileResponse;
import im.fooding.core.model.coupon.ProvideType;
import im.fooding.core.model.pointshop.PointShop;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPointShopResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "상품이름", example = "계란김밥 증정 쿠폰", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "교환 포인트", example = "10", requiredMode = RequiredMode.REQUIRED)
    private int point;

    @Schema(description = "ALL(모든고객), REGULAR_CUSTOMER(단골전용)", example = "ALL", requiredMode = RequiredMode.REQUIRED)
    private ProvideType provideType;

    @Schema(description = "사용 조건", example = "가게에서 사용 가능", requiredMode = RequiredMode.NOT_REQUIRED)
    private String conditions;

    @Schema(description = "남은 수량 null 이면 제한 없음", example = "10", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer quantity;

    @Schema(description = "이미지", requiredMode = RequiredMode.NOT_REQUIRED)
    private FileResponse image;

    @Builder
    private UserPointShopResponse(Long id, String name, int point, ProvideType provideType, String conditions, Integer quantity, FileResponse image) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.provideType = provideType;
        this.conditions = conditions;
        this.quantity = quantity;
        this.image = image;
    }

    public static UserPointShopResponse of(PointShop pointShop) {
        Integer quantity = null;
        if (pointShop.getTotalQuantity() != null) {
            quantity = Math.max(0, pointShop.getTotalQuantity() - pointShop.getIssuedQuantity());
        }

        return UserPointShopResponse.builder()
                .id(pointShop.getId())
                .name(pointShop.getName())
                .point(pointShop.getPoint())
                .provideType(pointShop.getProvideType())
                .conditions(pointShop.getConditions())
                .quantity(quantity)
                .image(pointShop.getImage() != null ? FileResponse.of(pointShop.getImage()) : null)
                .build();
    }
}
