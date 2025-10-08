package im.fooding.app.dto.request.ceo.coupon;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.coupon.UserCouponSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CeoSearchUserCouponRequest extends BasicSearch {
    @Schema(
            description = "정렬 타입(RECENT, OLD)",
            example = "RECENT",
            allowableValues = {"RECENT", "OLD"}
    )
    private UserCouponSortType sortType = UserCouponSortType.RECENT;
}
