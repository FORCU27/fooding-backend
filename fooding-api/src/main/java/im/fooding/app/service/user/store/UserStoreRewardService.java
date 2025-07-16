package im.fooding.app.service.user.store;

import im.fooding.app.dto.response.user.reward.UserStoreRewardResponse;
import im.fooding.core.model.coupon.BenefitType;
import im.fooding.core.model.coupon.DiscountType;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.reward.*;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.pointshop.PointShopService;
import im.fooding.core.service.reward.RewardLogService;
import im.fooding.core.service.reward.RewardService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreRewardService {
    private final PointShopService pointShopService;
    private final RewardService rewardService;
    private final UserCouponService userCouponService;
    private final UserService userService;
    private final StoreService storeService;
    private final RewardLogService rewardLogService;

    @Transactional(readOnly = true)
    public UserStoreRewardResponse list(long storeId, long userId) {
        List<PointShop> list = pointShopService.list(storeId, true, LocalDate.now());
        RewardPoint rewardPoint = rewardService.findByUserIdAndStoreId(userId, storeId);
        return UserStoreRewardResponse.of(rewardPoint != null ? rewardPoint.getPoint() : 0, list);
    }

    @Transactional
    public void purchase(long storeId, long id, long userId) {
        User user = userService.findById(userId);
        Store store = storeService.findById(storeId);
        PointShop pointShop = pointShopService.findById(id);

        //보유 포인트 조회
        RewardPoint rewardPoint = rewardService.findByUserIdAndStoreId(user.getId(), store.getId());

        //구매 처리
        pointShopService.issue(id);
        rewardPoint.usePoint(pointShop.getPoint());

        //리워드 사용 내역 추가
        rewardLogService.create(store, rewardPoint.getPhoneNumber(), pointShop.getPoint(), RewardStatus.USED, RewardType.VISIT, RewardChannel.STORE);
        
        //쿠폰생성
        userCouponService.create(null, user, store, BenefitType.GIFT, DiscountType.FIXED, 0, pointShop.getName(),
                pointShop.getConditions(), null, pointShop.getPoint());
    }
}
