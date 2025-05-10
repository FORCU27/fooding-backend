package im.fooding.core.model.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BenefitType {
    DISCOUNT("할인"), GIFT("증정");

    private final String name;
}
