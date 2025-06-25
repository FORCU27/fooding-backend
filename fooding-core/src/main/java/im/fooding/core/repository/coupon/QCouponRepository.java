package im.fooding.core.repository.coupon;

import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.CouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QCouponRepository {
    Page<Coupon> list(Long storeId, CouponStatus status, String searchString, Pageable pageable);
}
