package im.fooding.core.repository.coupon;

import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.coupon.UserCouponSortType;
import im.fooding.core.model.coupon.UserCouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QUserCouponRepository {
    Page<UserCoupon> list(Long userId, Long storeId, Long couponId, Boolean used, UserCouponStatus status, UserCouponSortType sortType, Pageable pageable);
}
