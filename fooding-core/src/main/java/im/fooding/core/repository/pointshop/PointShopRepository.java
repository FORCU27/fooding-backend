package im.fooding.core.repository.pointshop;

import im.fooding.core.model.pointshop.PointShop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointShopRepository extends JpaRepository<PointShop, Long>, QPointShopRepository {
}
