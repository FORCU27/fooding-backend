package im.fooding.core.model.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountType {
    PERCENT("퍼센트"), FIXED("고정");

    private final String name;
}
