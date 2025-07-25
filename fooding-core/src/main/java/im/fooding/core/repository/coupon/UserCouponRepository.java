package im.fooding.core.repository.coupon;

import im.fooding.core.model.coupon.UserCoupon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long>, QUserCouponRepository {
    @Override
    @EntityGraph(attributePaths = {"coupon", "user", "store"})
    Optional<UserCoupon> findById(Long id);

    boolean existsByCouponIdAndUserIdAndDeletedIsFalse(Long couponId, Long userId);

    @EntityGraph(attributePaths = {"coupon", "user", "store"})
    List<UserCoupon> findByUserIdAndCouponIdIn(Long userId, List<Long> couponIds);
}
