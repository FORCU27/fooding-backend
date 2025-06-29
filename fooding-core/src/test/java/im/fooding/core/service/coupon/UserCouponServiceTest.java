package im.fooding.core.service.coupon;

import im.fooding.core.TestConfig;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.*;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.*;
import im.fooding.core.repository.coupon.CouponRepository;
import im.fooding.core.repository.coupon.UserCouponRepository;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.repository.user.UserAuthorityRepository;
import im.fooding.core.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Sql(statements = "ALTER TABLE user_coupon ALTER COLUMN ID RESTART WITH 1", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserCouponServiceTest extends TestConfig {
    @Autowired
    UserCouponService userCouponService;

    @Autowired
    UserCouponRepository userCouponRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    @Test
    @DisplayName("유저에게 쿠폰을 성공적으로 발급한다.")
    void testCreate() {
        // given
        final Long id = 1L;
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        User user = saveUser();

        // when
        userCouponService.create(savedCoupon, user, savedCoupon.getStore(), savedCoupon.getBenefitType(), savedCoupon.getDiscountType(), savedCoupon.getDiscountValue(), savedCoupon.getName(), savedCoupon.getConditions(), savedCoupon.getExpiredOn());

        // then
        assertTrue(userCouponRepository.findById(id).isPresent());
    }

    @Test
    @DisplayName("유저에게 발급된 쿠폰 리스트를 성공적으로 조회한다.")
    void testList() {
        // given
        final String name1 = "테스트쿠폰1";
        final String name2 = "테스트쿠폰2";
        final int totalQuantity = 10;
        Coupon savedCoupon1 = saveCoupon(name1, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        Coupon savedCoupon2 = saveCoupon(name2, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        User user = saveUser();

        UserCoupon savedUserCoupon1 = saveUserCoupon(savedCoupon1, user);
        UserCoupon savedUserCoupon2 = saveUserCoupon(savedCoupon2, user);

        BasicSearch search = new BasicSearch();

        // when
        Page<UserCoupon> userCoupons = userCouponService.list(null, null, null, null, search.getPageable());

        // then
        assertThat(userCoupons.getContent()).hasSize(2)
                .extracting("id", "name", "discountValue")
                .containsExactlyInAnyOrder(
                        tuple(savedUserCoupon2.getId(), savedUserCoupon2.getName(), savedUserCoupon2.getDiscountValue()),
                        tuple(savedUserCoupon1.getId(), savedUserCoupon1.getName(), savedUserCoupon1.getDiscountValue())
                );
    }

    @Test
    @DisplayName("유저에게 발급된 쿠폰을 성공적으로 조회한다.")
    void testFindById() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        User user = saveUser();
        UserCoupon savedUserCoupon = saveUserCoupon(savedCoupon, user);

        // when
        UserCoupon userCoupon = userCouponService.findById(savedUserCoupon.getId());

        // then
        assertEquals(savedUserCoupon.getId(), userCoupon.getId());
        assertEquals(savedUserCoupon.getName(), userCoupon.getName());
        assertEquals(savedUserCoupon.getDiscountValue(), userCoupon.getDiscountValue());
    }

    @Test
    @DisplayName("유저에게 발급된 쿠폰를 성공적으로 사용한다.")
    void testUse() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        User user = saveUser();
        UserCoupon savedUserCoupon = saveUserCoupon(savedCoupon, user);

        // when
        userCouponService.request(savedUserCoupon);

        // then
        assertEquals(UserCouponStatus.REQUESTED, savedUserCoupon.getStatus());
    }

    @Test
    @DisplayName("유저에게 발급된 쿠폰이 이미 사용되어 사용에 실패한다")
    void testUse_fail_alreadyUsed() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        User user = saveUser();
        UserCoupon savedUserCoupon = saveUserCoupon(savedCoupon, user);
        userCouponService.request(savedUserCoupon);

        // when && then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userCouponService.request(savedUserCoupon);
                });
        assertEquals(ErrorCode.USER_COUPON_ALREADY_USE, apiException.getErrorCode());
    }

    @Test
    @DisplayName("유저에게 발급된 쿠폰이 유효 기간이 만료되어 사용에 실패한다")
    void testUse_fail_expired() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now().minusDays(1), LocalDate.now().minusDays(1), LocalDate.now().minusDays(1));
        User user = saveUser();
        UserCoupon savedUserCoupon = saveUserCoupon(savedCoupon, user);

        // when && then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userCouponService.request(savedUserCoupon);
                });
        assertEquals(ErrorCode.USER_COUPON_EXPIRED, apiException.getErrorCode());
    }

    @Test
    @DisplayName("유저에게 발급된 쿠폰을 성공적으로 삭제한다.")
    void testDelete() {
        // given
        final String name = "테스트쿠폰";
        final int totalQuantity = 10;
        Coupon savedCoupon = saveCoupon(name, totalQuantity, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusMonths(5));
        User user = saveUser();
        UserCoupon savedUserCoupon = saveUserCoupon(savedCoupon, user);

        // when
        userCouponService.delete(savedUserCoupon, 1L);

        // then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userCouponService.findById(savedUserCoupon.getId());
                });
        assertEquals(ErrorCode.USER_COUPON_NOT_FOUND, apiException.getErrorCode());
    }

    private User saveUser() {
        User user = User.builder()
                .email("test@gmail.com")
                .nickname("닉네임")
                .password("1234")
                .gender(Gender.NONE)
                .provider(AuthProvider.FOODING)
                .build();

        userRepository.save(user);

        UserAuthority userAuthority = UserAuthority.builder()
                .user(user)
                .role(Role.USER)
                .build();
        userAuthorityRepository.save(userAuthority);

        return user;
    }

    private Store saveStore() {
        Store store = Store.builder()
                .name("테스트가게")
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

    private UserCoupon saveUserCoupon(Coupon coupon, User user) {
        UserCoupon userCoupon = UserCoupon.builder()
                .coupon(coupon)
                .user(user)
                .store(coupon.getStore())
                .benefitType(coupon.getBenefitType())
                .discountType(coupon.getDiscountType())
                .name(coupon.getName())
                .conditions(coupon.getConditions())
                .expiredOn(coupon.getExpiredOn())
                .build();
        return userCouponRepository.save(userCoupon);
    }

}
