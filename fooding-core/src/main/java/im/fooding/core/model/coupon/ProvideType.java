package im.fooding.core.model.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProvideType {
    ALL("모든고객"), REGULAR_CUSTOMER("단골전용");
    
    private final String name;
}
