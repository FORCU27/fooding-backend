package im.fooding.core.service.coupon;

import im.fooding.core.TestConfig;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.*;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.coupon.CouponRepository;
import im.fooding.core.repository.region.RegionRepository;
import im.fooding.core.repository.store.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Sql(statements = "ALTER TABLE coupon ALTER COLUMN ID RESTART WITH 1", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CouponServiceTest extends TestConfig {
    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    RegionRepository regionRepository;

    @Test
    @DisplayName("쿠폰을 성공적으로 등록한다.")
    void testCreate() {
        // given
        final Long id = 1L;

        // when
        couponService.create(saveStore(), BenefitType.DISCOUNT, CouponType.GENERAL, DiscountType.FIXED, ProvideType.ALL,
                "테스트쿠폰", null, 10, 3000, LocalDate.now(),
                null, LocalDate.now().plusMonths(4), CouponStatus.ACTIVE);

        // then
        assertTrue(couponRepository.findById(id).isPresent());
    }

    @Test
    @DisplayName("쿠폰을 성공적으로 수정한다.")
    void testUpdate() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;

        final String updateName = "테스트쿠폰수정";
        final int updateTotalQuantity = 20;
        final int updateDiscountValue = 2000;

        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));

        // when
        couponService.update(savedCoupon.getId(), savedCoupon.getStore(), BenefitType.DISCOUNT, CouponType.GENERAL, DiscountType.FIXED, ProvideType.ALL,
                updateName, null, updateTotalQuantity, updateDiscountValue, LocalDate.now(),
                null, LocalDate.now().plusMonths(4), CouponStatus.ACTIVE);

        // then
        assertNotEquals(name, savedCoupon.getName());
        assertEquals(updateName, savedCoupon.getName());
        assertEquals(updateTotalQuantity, savedCoupon.getTotalQuantity());
        assertEquals(updateDiscountValue, savedCoupon.getDiscountValue());
    }

    @Test
    @DisplayName("쿠폰을 성공적으로 조회한다.")
    void testFindById() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));

        // when
        Coupon coupon = couponService.findById(savedCoupon.getId());

        // then
        assertEquals(savedCoupon.getId(), coupon.getId());
        assertEquals(savedCoupon.getStore().getId(), coupon.getStore().getId());
        assertEquals(savedCoupon.getBenefitType(), coupon.getBenefitType());
        assertEquals(savedCoupon.getType(), coupon.getType());
        assertEquals(savedCoupon.getDiscountType(), coupon.getDiscountType());
        assertEquals(savedCoupon.getProvideType(), coupon.getProvideType());
        assertEquals(savedCoupon.getName(), coupon.getName());
        assertEquals(savedCoupon.getTotalQuantity(), coupon.getTotalQuantity());
        assertEquals(savedCoupon.getDiscountValue(), coupon.getDiscountValue());
    }

    @Test
    @DisplayName("쿠폰 리스트를 성공적으로 조회 한다.")
    void testList() {
        // given
        final String name1 = "테스트쿠폰1";
        final String name2 = "테스트쿠폰2";
        final LocalDate issueStartOn1 = LocalDate.now();
        final LocalDate issueStartOn2 = LocalDate.now().plusMonths(1);
        final int totalQuantity1 = 10;
        final int totalQuantity2 = 20;

        BasicSearch search = new BasicSearch();

        Coupon coupon1 = saveCoupon(name1, totalQuantity1, issueStartOn1, LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        Coupon coupon2 = saveCoupon(name2, totalQuantity2, issueStartOn2, LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));

        // when
        Page<Coupon> coupons = couponService.list(null, null, null, search.getSearchString(), search.getPageable());

        // then
        assertThat(coupons.getContent()).hasSize(2)
                .extracting("id", "name", "issueStartOn")
                .containsExactlyInAnyOrder(
                        tuple(coupon2.getId(), coupon2.getName(), coupon2.getIssueStartOn()),
                        tuple(coupon1.getId(), coupon1.getName(), coupon1.getIssueStartOn())
                );
    }

    @Test
    @DisplayName("쿠폰을 성공적으로 삭제한다.")
    void testDelete() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon coupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));

        // when
        couponService.delete(coupon, 1L);

        // then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    couponService.findById(coupon.getId());
                });
        assertEquals(ErrorCode.COUPON_NOT_FOUND, apiException.getErrorCode());
    }

    @Test
    @DisplayName("쿠폰을 성공적으로 발급한다.")
    void testIssue() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon coupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));

        // when
        couponService.issue(coupon);

        // then
        assertEquals(1, coupon.getIssuedQuantity());
    }

    @Test
    @DisplayName("쿠폰 발급할 수량 부족으로 발행 실패한다.")
    void testIssue_fail_availableIssue() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 1;
        Coupon coupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        couponService.issue(coupon);

        // when && then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    couponService.issue(coupon);
                });
        assertEquals(ErrorCode.COUPON_ISSUE_QUANTITY_INVALID, apiException.getErrorCode());
    }

    @Test
    @DisplayName("쿠폰 발급 가능한 일자가 아니라 발행 실패한다.")
    void testIssue_fail_availableIssueDate() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon coupon = saveCoupon(name, totalQuantity, LocalDate.now().minusDays(1), LocalDate.now().minusDays(1), LocalDate.now().plusMonths(5));

        // when && then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    couponService.issue(coupon);
                });
        assertEquals(ErrorCode.COUPON_ISSUE_DATE_INVALID, apiException.getErrorCode());
    }


    private Store saveStore() {
        Store store = Store.builder()
                .name("테스트가게")
                .region(saveRegion())
                .city("테스트")
                .address("테스트")
                .category("테스트")
                .description("테스트")
                .priceCategory("테스트")
                .contactNumber("테스트")
                .direction("테스트")
                .information("테스트")
                .build();
        return storeRepository.save(store);
    }

    private Coupon saveCoupon(String name, int totalQuantity, LocalDate issueStartOn, LocalDate issueEndOn, LocalDate expiredOn) {
        Coupon coupon = Coupon.builder()
                .store(saveStore())
                .benefitType(BenefitType.DISCOUNT)
                .type(CouponType.GENERAL)
                .discountType(DiscountType.FIXED)
                .provideType(ProvideType.ALL)
                .name(name)
                .totalQuantity(totalQuantity)
                .discountValue(3000)
                .issueStartOn(issueStartOn)
                .issueEndOn(issueEndOn)
                .expiredOn(expiredOn)
                .status(CouponStatus.ACTIVE)
                .build();
        return couponRepository.save(coupon);
    }

    private Region saveRegion() {
        Region region = Region.builder()
                .id("테스트")
                .parentRegion(null)
                .name("테스트")
                .timezone("테스트")
                .countryCode("테스트")
                .legalCode("테스트")
                .currency("테스트")
                .level(1)
                .build();
        return regionRepository.save(region);
    }
}
