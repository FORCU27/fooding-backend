package im.fooding.core.service.coupon;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.BenefitType;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.DiscountType;
import im.fooding.core.model.coupon.UserCoupon;
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

    public void create(Coupon coupon, User user, Store store, BenefitType benefitType, DiscountType discountType,
                       String name, String conditions, LocalDate expiredOn) {
        UserCoupon userCoupon = UserCoupon.builder()
                .coupon(coupon)
                .user(user)
                .store(store)
                .benefitType(benefitType)
                .discountType(discountType)
                .name(name)
                .conditions(conditions)
                .expiredOn(expiredOn)
                .build();
        repository.save(userCoupon);
    }

    public Page<UserCoupon> list(Long userId, Long storeId, Pageable pageable) {
        return repository.list(userId, storeId, pageable);
    }

    public UserCoupon findById(long id) {
        return repository.findById(id).filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_COUPON_NOT_FOUND));
    }

    public void use(long id) {
        UserCoupon userCoupon = findById(id);
        userCoupon.use();
    }

    public void delete(long id, long deletedBy) {
        UserCoupon userCoupon = findById(id);
        userCoupon.delete(deletedBy);
    }
}
