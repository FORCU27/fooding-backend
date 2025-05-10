package im.fooding.app.dto.request.admin.coupon;

import im.fooding.core.common.BasicSearch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchUserCouponRequest extends BasicSearch {
    private Long userId;
    private Long storeId;
}
