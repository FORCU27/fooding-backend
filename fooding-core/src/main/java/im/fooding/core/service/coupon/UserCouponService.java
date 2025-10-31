package im.fooding.core.service.coupon;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.*;
import im.fooding.core.model.reward.RewardHistory;
import im.fooding.core.model.reward.RewardHistoryStatus;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.coupon.UserCouponRepository;
import im.fooding.core.service.reward.RewardHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCouponService {
    private final UserCouponRepository repository;
    private final RewardHistoryService rewardHistoryService;

    public UserCoupon create(Coupon coupon, User user, Store store, BenefitType benefitType, DiscountType discountType,
                             int discountValue, String name, String conditions, LocalDate expiredOn, Integer point, Long pointShopId) {
        if (null != coupon) {
            checkExistsCoupon(coupon.getId(), user.getId());
        }

        UserCoupon userCoupon = UserCoupon.builder()
                .coupon(coupon)
                .user(user)
                .store(store)
                .benefitType(benefitType)
                .discountType(discountType)
                .discountValue(discountValue)
                .name(name)
                .conditions(conditions)
                .expiredOn(expiredOn)
                .point(point)
                .pointShopId(pointShopId)
                .build();
        UserCoupon savedCoupon = repository.save(userCoupon);

        // 로깅
        this.logging( user.getPhoneNumber(), store, savedCoupon.getId(), false, RewardHistoryStatus.REQUEST, "" );

        return savedCoupon;
    }

    public Page<UserCoupon> list(Long userId, Long storeId, Long couponId, Boolean used, UserCouponStatus status, UserCouponSortType sortType, Pageable pageable) {
        return repository.list(userId, storeId, couponId, used, status, sortType, pageable);
    }

    public UserCoupon findById(long id) {
        return repository.findById(id).filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_COUPON_NOT_FOUND));
    }

    public void request(UserCoupon userCoupon, String tableNumber) {
        userCoupon.request(tableNumber);

        // 로깅
        this.logging(
                userCoupon.getUser().getPhoneNumber(),
                userCoupon.getStore(),
                userCoupon.getId(),
                true,
                RewardHistoryStatus.REQUEST,
                ""
        );
    }

    public void approve(UserCoupon userCoupon) {
        userCoupon.approve();
        // 로깅
        this.logging(
                userCoupon.getUser().getPhoneNumber(),
                userCoupon.getStore(),
                userCoupon.getId(),
                true,
                RewardHistoryStatus.APPLIED,
                ""
        );
    }

    public void delete(UserCoupon userCoupon, long deletedBy) {
        userCoupon.delete(deletedBy);
    }

    private void checkExistsCoupon(long couponId, long userId) {
        boolean exists = repository.existsByCouponIdAndUserIdAndDeletedIsFalse(couponId, userId);
        if (exists) {
            throw new ApiException(ErrorCode.USER_COUPON_ALREADY_ISSUE);
        }
    }

    public List<UserCoupon> findByUserIdAndCouponIds(Long userId, List<Long> couponIds) {
        return repository.findByUserIdAndCouponIdIn(userId, couponIds);
    }

    public List<UserCoupon> findByPointShopId(Long pointShopId) {
        return repository.findByPointShopId(pointShopId).stream()
                .filter(it -> !it.isDeleted())
                .toList();
    }

    // 로깅 메서드
    private void logging(
            String phoneNumber,
            Store store,
            long userCouponId,
            boolean isUsing,
            RewardHistoryStatus historyStatus,
            String memo
    ) {
        RewardHistory history = RewardHistory.builder()
                .phoneNumber( phoneNumber )
                .store( store )
                .isCoupon( true )
                .targetId( userCouponId )
                .isUsing( isUsing )
                .status( historyStatus )
                .memo( memo )
                .build();
        rewardHistoryService.create(history);
    }
}
