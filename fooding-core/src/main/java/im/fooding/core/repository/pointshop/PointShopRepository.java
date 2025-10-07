package im.fooding.core.repository.pointshop;

import im.fooding.core.model.pointshop.PointShop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointShopRepository extends JpaRepository<PointShop, Long>, QPointShopRepository {
    @EntityGraph(attributePaths = {"image"})
    Optional<PointShop> findById(Long id);
}
