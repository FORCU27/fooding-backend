package im.fooding.app.service.user.store;

import im.fooding.app.dto.response.user.reward.UserStoreRewardResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.BenefitType;
import im.fooding.core.model.coupon.DiscountType;
import im.fooding.core.model.coupon.ProvideType;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.pointshop.PointShopSortType;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardPoint;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.bookmark.BookmarkService;
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
    private final BookmarkService bookmarkService;

    @Transactional(readOnly = true)
    public UserStoreRewardResponse list(long storeId, UserInfo userInfo) {
        List<PointShop> list = pointShopService.list(storeId, true, LocalDate.now(), PointShopSortType.RECENT);
        RewardPoint rewardPoint = null;
        if (userInfo != null) {
            rewardService.findByUserIdAndStoreId(userInfo.getId(), storeId);
        }
        return UserStoreRewardResponse.of(rewardPoint != null ? rewardPoint.getPoint() : 0, list);
    }

    @Transactional
    public void purchase(long storeId, long id, long userId) {
        User user = userService.findById(userId);
        Store store = storeService.findById(storeId);
        PointShop pointShop = pointShopService.findById(id);

        //단골 확인
        if (ProvideType.REGULAR_CUSTOMER == pointShop.getProvideType()) {
            boolean exist = bookmarkService.existsByStoreIdAndUserId(pointShop.getStore().getId(), user.getId());
            if (!exist) {
                throw new ApiException(ErrorCode.STORE_BOOKMARK_NOT_FOUND);
            }
        }

        //보유 포인트 조회
        RewardPoint rewardPoint = rewardService.findByUserIdAndStoreId(user.getId(), store.getId());
        if (rewardPoint == null) {
            throw new ApiException(ErrorCode.REWARD_POINT_NOT_ENOUGH);
        }

        //구매 처리 및 포인트 사용
        pointShopService.issue(id);
        rewardService.usePoint(rewardPoint, pointShop.getPoint());

        //리워드 포인트 사용 내역 추가
        rewardLogService.create(store, rewardPoint.getPhoneNumber(), pointShop.getPoint(), RewardStatus.USED, RewardType.VISIT, RewardChannel.STORE);

        //쿠폰지급
        userCouponService.create(null, user, store, BenefitType.GIFT, DiscountType.FIXED, 0, pointShop.getName(),
                pointShop.getConditions(), null, pointShop.getPoint());
    }
}
