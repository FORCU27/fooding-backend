package im.fooding.core.repository.coupon;

import im.fooding.core.model.coupon.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QCouponRepository {
    Page<Coupon> list(Long storeId, String searchString, Pageable pageable);
}
