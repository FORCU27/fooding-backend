package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.pointshop.AdminCreatePointShopRequest;
import im.fooding.app.dto.request.admin.pointshop.AdminSearchPointShopRequest;
import im.fooding.app.dto.request.admin.pointshop.AdminUpdatePointShopRequest;
import im.fooding.app.dto.response.admin.pointshop.AdminPointShopResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.pointshop.PointShopService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminStorePointShopService {
    private final PointShopService pointShopService;
    private final StoreService storeService;

    @Transactional
    public Long create(AdminCreatePointShopRequest request) {
        Store store = storeService.findById(request.getStoreId());
        return pointShopService.create(store, request.getName(), request.getPoint(), request.getProvideType(), request.getConditions(),
                request.getTotalQuantity(), request.getIssueStartOn(), request.getIssueEndOn()).getId();
    }

    @Transactional
    public void update(long id, AdminUpdatePointShopRequest request) {
        pointShopService.update(id, request.getName(), request.getPoint(), request.getProvideType(), request.getConditions(),
                request.getTotalQuantity(), request.getIssueStartOn(), request.getIssueEndOn());
    }

    @Transactional(readOnly = true)
    public AdminPointShopResponse retrieve(long id) {
        PointShop pointShop = pointShopService.findById(id);
        return AdminPointShopResponse.of(pointShop);
    }

    @Transactional(readOnly = true)
    public PageResponse<AdminPointShopResponse> list(AdminSearchPointShopRequest search) {
        Page<PointShop> list = pointShopService.list(search.getStoreId(), search.getIsActive(), null, search.getSearchString(), search.getPageable());
        return PageResponse.of(list.stream().map(AdminPointShopResponse::of).toList(), PageInfo.of(list));
    }

    @Transactional
    public void delete(long id, long adminId) {
        pointShopService.delete(id, adminId);
    }

    @Transactional
    public void active(long id) {
        pointShopService.active(id);
    }

    @Transactional
    public void inactive(long id) {
        pointShopService.inactive(id);
    }
}
