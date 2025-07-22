package im.fooding.app.service.user.coupon;

import im.fooding.app.dto.request.user.coupon.UserSearchStoreCouponRequest;
import im.fooding.app.dto.response.user.coupon.UserStoreCouponResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.CouponStatus;
import im.fooding.core.model.coupon.ProvideType;
import im.fooding.core.model.user.User;
import im.fooding.core.service.bookmark.BookmarkService;
import im.fooding.core.service.coupon.CouponService;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreCouponService {
    private final UserService userService;
    private final CouponService couponService;
    private final UserCouponService userCouponService;
    private final BookmarkService bookmarkService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreCouponResponse> list(UserSearchStoreCouponRequest search, UserInfo userInfo) {
        Page<Coupon> list = couponService.list(search.getStoreId(), CouponStatus.ACTIVE, LocalDate.now(), search.getSearchString(), search.getPageable());
        List<UserStoreCouponResponse> response = list.stream().map(UserStoreCouponResponse::of).toList();
        List<Long> couponIds = response.stream().map(UserStoreCouponResponse::getId).toList();

        //로그인시 발급 여부 세팅
        if (userInfo != null) {
            Set<Long> issuedCouponIds = userCouponService.findByUserIdAndCouponIds(userInfo.getId(), couponIds).stream()
                    .map(userCoupon -> userCoupon.getCoupon().getId())
                    .collect(Collectors.toSet());

            for (UserStoreCouponResponse it : response) {
                if (issuedCouponIds.contains(it.getId())) {
                    it.setIsCouponIssued();
                }
            }
        }
        return PageResponse.of(response, PageInfo.of(list));
    }

    @Transactional
    public Long issue(long id, long userId) {
        Coupon coupon = couponService.findById(id);
        User user = userService.findById(userId);

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
}
