package im.fooding.app.dto.request.user.coupon;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.coupon.UserCouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchUserCouponRequest extends BasicSearch {
    @Schema(description = "가게 id", example = "1")
    private Long storeId;

    @Schema(description = "사용 여부", example = "true")
    private Boolean used;
}
