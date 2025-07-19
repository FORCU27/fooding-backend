package im.fooding.core.service.coupon;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.*;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.coupon.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCouponService {
    private final UserCouponRepository repository;

    public UserCoupon create(Coupon coupon, User user, Store store, BenefitType benefitType, DiscountType discountType,
                             int discountValue, String name, String conditions, LocalDate expiredOn, Integer point) {
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
                .build();
        return repository.save(userCoupon);
    }

    public Page<UserCoupon> list(Long userId, Long storeId, Boolean used, UserCouponStatus status, Pageable pageable) {
        return repository.list(userId, storeId, used, status, pageable);
    }

    public UserCoupon findById(long id) {
        return repository.findById(id).filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_COUPON_NOT_FOUND));
    }

    public void request(UserCoupon userCoupon, String tableNumber) {
        userCoupon.request(tableNumber);
    }

    public void approve(UserCoupon userCoupon) {
        userCoupon.approve();
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
}
