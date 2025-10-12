package im.fooding.app.service.admin.coupon;

import im.fooding.app.dto.request.admin.coupon.AdminGiftCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminIssueCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminSearchUserCouponRequest;
import im.fooding.app.dto.response.admin.coupon.AdminUserCouponResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.ProvideType;
import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.bookmark.BookmarkService;
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
    private final BookmarkService bookmarkService;

    @Transactional
    public void issue(AdminIssueCouponRequest request) {
        Coupon coupon = couponService.findById(request.getCouponId());
        User user = userService.findById(request.getUserId());

        //단골 확인
        if (ProvideType.REGULAR_CUSTOMER == coupon.getProvideType()) {
            boolean exist = bookmarkService.existsByStoreIdAndUserId(coupon.getStore().getId(), user.getId());
            if (!exist) {
                throw new ApiException(ErrorCode.STORE_BOOKMARK_NOT_FOUND);
            }
        }

        couponService.issue(coupon);

        userCouponService.create(coupon, user, coupon.getStore(), coupon.getBenefitType(), coupon.getDiscountType(),
                coupon.getDiscountValue(), coupon.getName(), coupon.getConditions(), coupon.getExpiredOn(), null, null);
    }

    @Transactional
    public void issueByGift(AdminGiftCouponRequest request) {
        User user = userService.findById(request.getUserId());
        userCouponService.create(null, user, getStore(request.getStoreId()), request.getBenefitType(),
                request.getDiscountType(), request.getDiscountValue(), request.getName(), request.getConditions(), request.getExpiredOn(), null, null);
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        UserCoupon userCoupon = userCouponService.findById(id);
        userCouponService.delete(userCoupon, deletedBy);
    }

    @Transactional(readOnly = true)
    public PageResponse<AdminUserCouponResponse> list(AdminSearchUserCouponRequest search) {
        Page<UserCoupon> userCoupons = userCouponService.list(
                search.getUserId(),
                search.getStoreId(),
                search.getCouponId(),
                null,
                search.getStatus(),
                search.getSortType(),
                search.getPageable()
        );
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
