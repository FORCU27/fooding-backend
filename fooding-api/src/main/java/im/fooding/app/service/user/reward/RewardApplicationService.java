package im.fooding.app.service.user.reward;


import im.fooding.app.dto.request.app.coupon.AppSearchCouponRequest;
import im.fooding.app.dto.request.user.reward.GetRewardLogRequest;
import im.fooding.app.dto.request.user.reward.GetRewardPointRequest;
import im.fooding.app.dto.request.user.reward.GetUserRewardLogRequest;
import im.fooding.app.dto.request.user.reward.UpdateRewardPointRequest;
import im.fooding.app.dto.response.app.coupon.AppUserCouponResponse;
import im.fooding.app.dto.response.user.reward.GetRewardLogResponse;
import im.fooding.app.dto.response.user.reward.GetRewardPointResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.event.coupon.RequestCouponEvent;
import im.fooding.core.event.reward.RewardEarnEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.user.User;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.reward.RewardLogService;
import im.fooding.core.service.reward.RewardService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardApplicationService {
    private final RewardLogService logService;
    private final RewardService pointService;
    private final StoreService storeService;
    private final UserService userService;
    private final UserCouponService userCouponService;
    private final ApplicationEventPublisher publisher;
    private final EventProducerService eventProducerService;

    @Value("${message.sender}")
    private String SENDER;

    /**
     * Reward Log 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
    public Page<GetRewardLogResponse> getRewardLog(GetRewardLogRequest request){
        return logService.list(request.getSearchString(), request.getPageable(), request.getStoreId(), request.getPhoneNumber(), request.getStatus()).map(GetRewardLogResponse::of);
    }

    /**
     * Reward 개인 로그 조회
     *
     * @param userId
     * @param request
     * @return PageResponse<GetRewardPointResponse>
     */
    public PageResponse<GetRewardLogResponse> getUserRewardLog(long userId, GetUserRewardLogRequest request){
        List<GetRewardLogResponse> response = new ArrayList<GetRewardLogResponse>();
        User user = userService.findById( userId );
        String phoneNumber = user.getPhoneNumber();

        Page<GetRewardLogResponse> result = logService.list( null, request.getPageable(), null, phoneNumber, request.getStatus() ).map( GetRewardLogResponse::of );
        if(!result.isEmpty() || result != null ) response = result.stream().toList();
        return PageResponse.of( response, PageInfo.of( result ) );
    }

    /**
     * Reward Point 조회
     *
     * @param request
     * @return Page<GetRewardPointResponse>
     */
    public Page<GetRewardPointResponse> getRewardPoint(GetRewardPointRequest request){
        return pointService.list( request.getSearchString(), request.getStoreId(), request.getPhoneNumber(), request.getPageable() ).map( GetRewardPointResponse::of );
    }

    public Long getLogCount( Long storeId, long userId, RewardStatus status ) {
        User user = userService.findById( userId );
        String phoneNumber = user.getPhoneNumber();

        if( phoneNumber == null ) return null;
        return logService.countList( null, storeId, phoneNumber, status );
    }


    /**
     * Reward 적립
     *
     * @param request
     */
    @Transactional
    public void earnPoint(UpdateRewardPointRequest request){
        Pageable pageable = PageRequest.of(0, 5);
        List<GetRewardPointResponse> result = pointService.list( null, request.getStoreId(), request.getPhoneNumber(), pageable ).map(GetRewardPointResponse::of).stream().collect(Collectors.toList());
        if( result.size() == 0 ) pointService.create(storeService.findById( request.getStoreId() ), request.getPhoneNumber(), request.getPoint(), null);
        else pointService.addPoint(request.getPhoneNumber(), request.getStoreId(), request.getPoint());
        logService.create(
                storeService.findById(request.getStoreId()),
                request.getPhoneNumber(),
                request.getPoint(),
                RewardStatus.EARNED,
                request.getType(),
                request.getChannel()
        );
        sendNotification(request.getPhoneNumber(), request.getStoreId(), request.getPoint());
    }

    /**
     * Reward 사용
     *
     * @param request
     */
    public void usePoint(UpdateRewardPointRequest request){
        // Point가 있는지 확인
        //  데이터가 존재하는지 확인
        Pageable pageable = PageRequest.of(0, 5);
        List<GetRewardPointResponse> result = pointService.list( null, request.getStoreId(), request.getPhoneNumber(), pageable ).map(GetRewardPointResponse::of).stream().collect(Collectors.toList());
        if( result.size() == 0 ) throw new ApiException(ErrorCode.REWARD_NOT_FOUND);
        //  잔여 포인트가 있는지 확인
        if( result.get(0).getPoint() < request.getPoint() )  throw new ApiException(ErrorCode.REWARD_POINT_NOT_ENOUGH);
        // Point가 있는 경우 소비
        pointService.usePoint( request.getPhoneNumber(), request.getStoreId(), request.getPoint() );
        logService.create(
                storeService.findById(request.getStoreId()),
                request.getPhoneNumber(),
                request.getPoint(),
                RewardStatus.USED,
                request.getType(),
                request.getChannel()
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<AppUserCouponResponse> getRewardCoupons(AppSearchCouponRequest search) {
        User user = userService.findByPhoneNumber(search.getPhoneNumber());
        Page<UserCoupon> coupons = userCouponService.list(user.getId(), search.getStoreId(), search.getUsed(), null, search.getPageable());
        List<AppUserCouponResponse> list = coupons.getContent().stream().map(AppUserCouponResponse::of).toList();
        return PageResponse.of(list, PageInfo.of(coupons));
    }

    @Transactional
    public void requestCoupon(Long couponId) {
        UserCoupon userCoupon = userCouponService.findById(couponId);
        userCouponService.request(userCoupon, null);
        publisher.publishEvent(new RequestCouponEvent(userCoupon.getName(), userCoupon.getUser().getPhoneNumber(), SENDER, NotificationChannel.SMS));
    }

    private void sendNotification(String phoneNumber, long storeId, int point) {
        String storeName = storeService.findById(storeId).getName();

        eventProducerService.publishEvent(
                RewardEarnEvent.class.getSimpleName(),
                new RewardEarnEvent(phoneNumber, storeName, point)
        );
    }
}
