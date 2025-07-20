package im.fooding.app.service.user.coupon;

import im.fooding.app.dto.request.user.coupon.UserSearchStoreCouponRequest;
import im.fooding.app.dto.response.user.coupon.UserStoreCouponResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.CouponStatus;
import im.fooding.core.model.user.User;
import im.fooding.core.service.coupon.CouponService;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreCouponService {
    private final UserService userService;
    private final CouponService couponService;
    private final UserCouponService userCouponService;

    @Transactional(readOnly = true)
    public PageResponse list(UserSearchStoreCouponRequest search) {
        Page<Coupon> list = couponService.list(search.getStoreId(), CouponStatus.ACTIVE, LocalDate.now(), search.getSearchString(), search.getPageable());
        return PageResponse.of(list.stream().map(UserStoreCouponResponse::of).toList(), PageInfo.of(list));
    }

    @Transactional
    public Long issue(long id, long userId) {
        Coupon coupon = couponService.findById(id);
        User user = userService.findById(userId);

        couponService.issue(coupon);

        return userCouponService.create(coupon, user, coupon.getStore(), coupon.getBenefitType(), coupon.getDiscountType(),
                coupon.getDiscountValue(), coupon.getName(), coupon.getConditions(), coupon.getExpiredOn(), null).getId();
    }
}
