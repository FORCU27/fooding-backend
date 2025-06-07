package im.fooding.app.service.admin.coupon;

import im.fooding.app.dto.request.admin.coupon.*;
import im.fooding.app.dto.response.admin.coupon.AdminCouponResponse;
import im.fooding.app.dto.response.admin.coupon.AdminUserCouponResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.coupon.CouponService;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserCouponService {
    private final UserCouponService userCouponService;
    private final UserService userService;
    private final CouponService couponService;
    private final StoreService storeService;

    @Transactional
    public void issue(AdminIssueCouponRequest request) {
        Coupon coupon = couponService.findById(request.getCouponId());
        User user = userService.findById(request.getUserId());
        couponService.issue(coupon);
        userCouponService.create(coupon, user, coupon.getStore(), coupon.getBenefitType(), coupon.getDiscountType(),
            coupon.getDiscountValue(), coupon.getName(), coupon.getConditions(), coupon.getExpiredOn());
    }

    @Transactional
    public void issueByGift(AdminGiftCouponRequest request) {
        User user = userService.findById(request.getUserId());
        userCouponService.create(null, user, getStore(request.getStoreId()), request.getBenefitType(),
            request.getDiscountType(), request.getDiscountValue(), request.getName(), request.getConditions(), request.getExpiredOn());
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        userCouponService.delete(id, deletedBy);
    }

    @Transactional(readOnly = true)
    public PageResponse<AdminUserCouponResponse> list(AdminSearchUserCouponRequest search) {
        Page<UserCoupon> userCoupons = userCouponService.list(search.getUserId(), search.getStoreId(), search.getUsed(), search.getPageable());
        List<AdminUserCouponResponse> list = userCoupons.getContent().stream().map(AdminUserCouponResponse::of).toList();
        return PageResponse.of(list, PageInfo.of(userCoupons));
    }

    @Transactional(readOnly = true)
    public AdminUserCouponResponse retrieve(long id) {
        UserCoupon userCoupon = userCouponService.findById(id);
        return AdminUserCouponResponse.of(userCoupon);
    }

    private Store getStore(Long storeId) {
        return null != storeId ? storeService.findById(storeId) : null;
    }
}
