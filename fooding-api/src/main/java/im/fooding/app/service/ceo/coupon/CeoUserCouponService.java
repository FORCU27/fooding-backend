package im.fooding.app.service.ceo.coupon;

import im.fooding.app.dto.request.ceo.coupon.CeoGiftCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoIssueCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoSearchUserCouponRequest;
import im.fooding.app.dto.response.ceo.coupon.CeoUserCouponResponse;
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
import im.fooding.core.service.store.StoreMemberService;
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
public class CeoUserCouponService {
    private final UserCouponService userCouponService;
    private final UserService userService;
    private final CouponService couponService;
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final BookmarkService bookmarkService;

    @Transactional
    public Long issue(CeoIssueCouponRequest request, long ceoId) {
        Coupon coupon = couponService.findById(request.getCouponId());
        User user = userService.findById(request.getUserId());
        storeMemberService.checkMember(coupon.getStore().getId(), ceoId);

        //단골 확인
        if (ProvideType.REGULAR_CUSTOMER == coupon.getProvideType()) {
            boolean exist = bookmarkService.existsByStoreIdAndUserId(coupon.getStore().getId(), user.getId());
            if (!exist) {
                throw new ApiException(ErrorCode.STORE_BOOKMARK_NOT_FOUND);
            }
        }

        couponService.issue(coupon);

        return userCouponService.create(coupon, user, coupon.getStore(), coupon.getBenefitType(), coupon.getDiscountType(),
                coupon.getDiscountValue(), coupon.getName(), coupon.getConditions(), coupon.getExpiredOn(), null).getId();
    }

    @Transactional
    public Long issueByGift(CeoGiftCouponRequest request, long ceoId) {
        User user = userService.findById(request.getUserId());

        Store store = storeService.findById(request.getStoreId());
        checkMember(store.getId(), ceoId);

        return userCouponService.create(null, user, store, request.getBenefitType(),
                request.getDiscountType(), request.getDiscountValue(), request.getName(), request.getConditions(), request.getExpiredOn(), null).getId();
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        UserCoupon userCoupon = userCouponService.findById(id);
        checkMember(userCoupon.getStore().getId(), deletedBy);
        userCouponService.delete(userCoupon, deletedBy);
    }

    @Transactional(readOnly = true)
    public PageResponse<CeoUserCouponResponse> list(CeoSearchUserCouponRequest search, long ceoId) {
        checkMember(search.getStoreId(), ceoId);
        Page<UserCoupon> userCoupons = userCouponService.list(search.getUserId(), search.getStoreId(), null, search.getStatus(), search.getPageable());
        List<CeoUserCouponResponse> list = userCoupons.getContent().stream().map(CeoUserCouponResponse::of).toList();
        return PageResponse.of(list, PageInfo.of(userCoupons));
    }

    @Transactional(readOnly = true)
    public CeoUserCouponResponse retrieve(long id, long ceoId) {
        UserCoupon userCoupon = userCouponService.findById(id);
        checkMember(userCoupon.getStore().getId(), ceoId);
        return CeoUserCouponResponse.of(userCoupon);
    }

    @Transactional
    public void approve(long id, long ceoId) {
        UserCoupon userCoupon = userCouponService.findById(id);
        checkMember(userCoupon.getStore().getId(), ceoId);
        userCouponService.approve(userCoupon);
    }

    private void checkMember(long storeId, long ceoId) {
        storeMemberService.checkMember(storeId, ceoId);
    }
}
