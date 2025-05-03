package im.fooding.core.model.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponType {
    GENERAL("일반"), FIRST_COME_FIRST_SERVED("선착순");

    private final String name;
}
