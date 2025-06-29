package im.fooding.core.service.coupon;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.*;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {
    private final CouponRepository repository;

    public Coupon create(Store store, BenefitType benefitType, CouponType type, DiscountType discountType, ProvideType provideType,
                         String name, String conditions, Integer totalQuantity, int discountValue, LocalDate issueStartOn,
                         LocalDate issueEndOn, LocalDate expiredOn, CouponStatus status) {
        Coupon coupon = Coupon.builder()
                .store(store)
                .benefitType(benefitType)
                .type(type)
                .discountType(discountType)
                .provideType(provideType)
                .name(name)
                .conditions(conditions)
                .totalQuantity(totalQuantity)
                .discountValue(discountValue)
                .issueStartOn(issueStartOn)
                .issueEndOn(issueEndOn)
                .expiredOn(expiredOn)
                .status(status)
                .build();
        return repository.save(coupon);
    }

    public void update(long id, Store store, BenefitType benefitType, CouponType couponType, DiscountType discountType,
                       ProvideType provideType, String name, String conditions, Integer totalQuantity, int discountValue,
                       LocalDate issueStartOn, LocalDate issueEndOn, LocalDate expiredOn, CouponStatus status) {
        Coupon coupon = findById(id);
        coupon.update(store, benefitType, couponType, discountType, provideType, name, conditions,
                totalQuantity, issueStartOn, issueEndOn, expiredOn, discountValue, status);
    }

    public Coupon findById(long id) {
        return repository.findById(id).filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.COUPON_NOT_FOUND));
    }

    public Page<Coupon> list(Long storeId, CouponStatus status, LocalDate now, String searchString, Pageable pageable) {
        return repository.list(storeId, status, now, searchString, pageable);
    }

    public void delete(Coupon coupon, Long deletedBy) {
        coupon.delete(deletedBy);
    }

    public void issue(Coupon coupon) {
        coupon.issue();
    }
}
