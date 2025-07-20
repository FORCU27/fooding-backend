package im.fooding.core.repository.pointshop;

import im.fooding.core.model.pointshop.PointShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface QPointShopRepository {
    Page<PointShop> list(Long storeId, boolean isActive, LocalDate now, String searchString, Pageable pageable);
    List<PointShop> list(Long storeId, boolean isActive, LocalDate now);
}
