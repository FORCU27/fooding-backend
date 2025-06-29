package im.fooding.app.service.ceo.coupon;

import im.fooding.app.dto.request.ceo.coupon.CeoCreateCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoSearchCouponRequest;
import im.fooding.app.dto.request.ceo.coupon.CeoUpdateCouponRequest;
import im.fooding.app.dto.response.ceo.coupon.CeoCouponResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.coupon.CouponService;
import im.fooding.core.service.store.StoreMemberService;
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
public class CeoCouponService {
    private final CouponService couponService;
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;

    @Transactional
    public Long create(CeoCreateCouponRequest request, long userId) {
        Store store = storeService.findById(request.getStoreId());
        checkMember(store.getId(), userId);

        return couponService.create(store, request.getBenefitType(), request.getType(),
                request.getDiscountType(), request.getProvideType(), request.getName(), request.getConditions(),
                request.getTotalQuantity(), request.getDiscountValue(), request.getIssueStartOn(), request.getIssueEndOn(),
                request.getExpiredOn(), request.getStatus()).getId();
    }

    @Transactional
    public void update(long id, CeoUpdateCouponRequest request, long userId) {
        Store store = storeService.findById(request.getStoreId());
        checkMember(store.getId(), userId);

        couponService.update(id, store, request.getBenefitType(), request.getType(),
                request.getDiscountType(), request.getProvideType(), request.getName(), request.getConditions(),
                request.getTotalQuantity(), request.getDiscountValue(), request.getIssueStartOn(), request.getIssueEndOn(),
                request.getExpiredOn(), request.getStatus());
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        Coupon coupon = couponService.findById(id);
        checkMember(coupon.getStore().getId(), deletedBy);
        couponService.delete(coupon, deletedBy);
    }

    @Transactional(readOnly = true)
    public PageResponse<CeoCouponResponse> list(CeoSearchCouponRequest search, long userId) {
        checkMember(search.getStoreId(), userId);
        Page<Coupon> coupons = couponService.list(search.getStoreId(), search.getStatus(), null, search.getSearchString(), search.getPageable());
        List<CeoCouponResponse> list = coupons.getContent().stream().map(CeoCouponResponse::of).toList();
        return PageResponse.of(list, PageInfo.of(coupons));
    }

    @Transactional(readOnly = true)
    public CeoCouponResponse retrieve(long id, long userId) {
        Coupon coupon = couponService.findById(id);
        checkMember(coupon.getStore().getId(), userId);
        return CeoCouponResponse.of(coupon);
    }

    private void checkMember(long storeId, long ceoId) {
        storeMemberService.checkMember(storeId, ceoId);
    }
}
