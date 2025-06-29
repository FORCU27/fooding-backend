package im.fooding.app.dto.request.admin.coupon;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.coupon.UserCouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchUserCouponRequest extends BasicSearch {
    @Schema(description = "유저 id", example = "1")
    private Long userId;

    @Schema(description = "가게 id", example = "1")
    private Long storeId;

    @Schema(description = "쿠폰 상태 (AVAILABLE, REQUESTED, USED)", example = "AVAILABLE")
    private UserCouponStatus status;
}
