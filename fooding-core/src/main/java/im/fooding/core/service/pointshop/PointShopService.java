package im.fooding.core.service.pointshop;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.coupon.ProvideType;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.pointshop.PointShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointShopService {
    private final PointShopRepository repository;

    public PointShop create(Store store, String name, int point, ProvideType provideType, String conditions, Integer totalQuantity, boolean isActive, LocalDate issueStartOn, LocalDate issueEndOn) {
        PointShop pointShop = PointShop.builder()
                .store(store)
                .name(name)
                .point(point)
                .provideType(provideType)
                .conditions(conditions)
                .totalQuantity(totalQuantity)
                .isActive(isActive)
                .issueStartOn(issueStartOn)
                .issueEndOn(issueEndOn)
                .build();
        return repository.save(pointShop);
    }

    public void update(long id, String name, int point, ProvideType provideType, String conditions, Integer totalQuantity, boolean isActive, LocalDate issueStartOn, LocalDate issueEndOn) {
        PointShop pointShop = findById(id);
        pointShop.update(name, point, provideType, conditions, totalQuantity, isActive, issueStartOn, issueEndOn);
    }

    public PointShop findById(long id) {
        return repository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.POINT_SHOP_NOT_FOUND));
    }

    public Page<PointShop> list(Long storeId, LocalDate now, String searchString, Pageable pageable) {
        return repository.list(storeId, now, searchString, pageable);
    }

    public void issue(PointShop pointShop) {
        pointShop.issue();
    }

    public void delete(long id, long deletedBy) {
        PointShop pointShop = findById(id);
        pointShop.delete(deletedBy);
    }
}
