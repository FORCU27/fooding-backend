package im.fooding.core.model.coupon;

import com.querydsl.core.types.OrderSpecifier;

public enum UserCouponSortType {
    RECENT {
        @Override
        public OrderSpecifier<?>[] getOrder(QUserCoupon userCoupon) {
            return new OrderSpecifier[]{
                    userCoupon.id.desc(),
            };
        }
    },
    OLD {
        @Override
        public OrderSpecifier<?>[] getOrder(QUserCoupon userCoupon) {
            return new OrderSpecifier[]{
                    userCoupon.id.asc(),
            };
        }
    };

    public abstract OrderSpecifier<?>[] getOrder(QUserCoupon userCoupon);
}
