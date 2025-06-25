package im.fooding.core.repository.coupon;

import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.CouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface QCouponRepository {
    Page<Coupon> list(Long storeId, CouponStatus status, LocalDate now, String searchString, Pageable pageable);
}
