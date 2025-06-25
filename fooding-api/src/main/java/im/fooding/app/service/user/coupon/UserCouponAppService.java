package im.fooding.app.service.user.coupon;

import im.fooding.app.dto.request.user.coupon.UserSearchUserCouponRequest;
import im.fooding.app.dto.response.user.coupon.UserCouponResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.event.coupon.RequestCouponEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.service.coupon.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCouponAppService {
    private final UserCouponService userCouponService;
    private final ApplicationEventPublisher publisher;

    @Value("${message.sender}")
    private String SENDER;

    @Transactional(readOnly = true)
    public PageResponse<UserCouponResponse> list(UserSearchUserCouponRequest search, long userId) {
        Page<UserCoupon> userCoupons = userCouponService.list(userId, search.getStoreId(), search.getUsed(), null, search.getPageable());
        return PageResponse.of(userCoupons.stream().map(UserCouponResponse::of).toList(), PageInfo.of(userCoupons));
    }

    @Transactional
    public void request(long id, Long userId) {
        UserCoupon userCoupon = userCouponService.findById(id);
        if (!userId.equals(userCoupon.getUser().getId())) {
            throw new ApiException(ErrorCode.USER_COUPON_NOT_FOUND);
        }
        userCouponService.request(userCoupon);
        publisher.publishEvent(new RequestCouponEvent(userCoupon.getName(), userCoupon.getUser().getPhoneNumber(), SENDER, NotificationChannel.SMS));
    }
}
