package im.fooding.core.model.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponStatus {
    ACTIVE("활성화"), INACTIVE("비활성화");

    private final String name;
}
