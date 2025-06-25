package im.fooding.app.service.admin.coupon;

import im.fooding.app.dto.request.admin.coupon.AdminCreateCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminSearchCouponRequest;
import im.fooding.app.dto.request.admin.coupon.AdminUpdateCouponRequest;
import im.fooding.app.dto.response.admin.coupon.AdminCouponResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.coupon.CouponService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCouponService {
    private final CouponService couponService;
    private final StoreService storeService;

    @Transactional
    public void create(AdminCreateCouponRequest request) {
        couponService.create(getStore(request.getStoreId()), request.getBenefitType(), request.getType(), request.getDiscountType(), request.getProvideType(),
                request.getName(), request.getConditions(), request.getTotalQuantity(), request.getDiscountValue(),
                request.getIssueStartOn(), request.getIssueEndOn(), request.getExpiredOn(), request.getStatus());
    }

    @Transactional
    public void update(long id, AdminUpdateCouponRequest request) {
        couponService.update(id, getStore(request.getStoreId()), request.getBenefitType(), request.getType(), request.getDiscountType(), request.getProvideType(),
                request.getName(), request.getConditions(), request.getTotalQuantity(), request.getDiscountValue(),
                request.getIssueStartOn(), request.getIssueEndOn(), request.getExpiredOn(), request.getStatus());
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        Coupon coupon = couponService.findById(id);
        couponService.delete(coupon, deletedBy);
    }

    @Transactional(readOnly = true)
    public PageResponse<AdminCouponResponse> list(AdminSearchCouponRequest search) {
        Page<Coupon> coupons = couponService.list(search.getStoreId(), search.getStatus(), search.getSearchString(), search.getPageable());
        List<AdminCouponResponse> list = coupons.getContent().stream().map(AdminCouponResponse::of).toList();
        return PageResponse.of(list, PageInfo.of(coupons));
    }

    @Transactional(readOnly = true)
    public AdminCouponResponse retrieve(long id) {
        Coupon coupon = couponService.findById(id);
        return AdminCouponResponse.of(coupon);
    }

    private Store getStore(Long storeId) {
        return null != storeId ? storeService.findById(storeId) : null;
    }
}
