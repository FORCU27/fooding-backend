package im.fooding.app.dto.response.user.reward;

import im.fooding.core.model.pointshop.PointShop;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserStoreRewardResponse {
    @Schema(description = "보유 포인트", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int point;
    
    @Schema(description = "포인트샵 상품 리스트", requiredMode = RequiredMode.REQUIRED)
    private List<UserPointShopResponse> pointShopItems;

    @Builder
    public UserStoreRewardResponse(int point, List<UserPointShopResponse> pointShopItems) {
        this.point = point;
        this.pointShopItems = pointShopItems;
    }

    public static UserStoreRewardResponse of(int point, List<PointShop> pointShops) {
        return UserStoreRewardResponse.builder()
                .point(point)
                .pointShopItems(pointShops.stream().map(UserPointShopResponse::of).collect(Collectors.toList()))
                .build();
    }
}
