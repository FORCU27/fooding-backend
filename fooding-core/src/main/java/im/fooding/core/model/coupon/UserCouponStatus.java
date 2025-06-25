package im.fooding.core.model.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserCouponStatus {
    AVAILABLE("사용 가능"),
    REQUESTED("사용 요청"),
    USED("사용 완료");

    private final String name;
}
