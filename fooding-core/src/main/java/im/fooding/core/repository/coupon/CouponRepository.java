package im.fooding.core.repository.coupon;

import im.fooding.core.model.coupon.Coupon;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, QCouponRepository {
    @Override
    @EntityGraph(attributePaths = {"store"})
    Optional<Coupon> findById(Long id);
}
