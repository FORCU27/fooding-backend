package im.fooding.core.repository.coupon;

import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.coupon.UserCouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QUserCouponRepository {
    Page<UserCoupon> list(Long userId, Long storeId, Long couponId, Boolean used, UserCouponStatus status, Pageable pageable);
}
