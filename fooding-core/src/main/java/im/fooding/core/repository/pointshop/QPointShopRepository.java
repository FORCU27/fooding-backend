package im.fooding.core.repository.pointshop;

import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.pointshop.PointShopSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface QPointShopRepository {
    Page<PointShop> list(Long storeId, Boolean isActive, LocalDate now, PointShopSortType sortType, String searchString, Pageable pageable);

    List<PointShop> list(Long storeId, Boolean isActive, LocalDate now, PointShopSortType sortType);
}
