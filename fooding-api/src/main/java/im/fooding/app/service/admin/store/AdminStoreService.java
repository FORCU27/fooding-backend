package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.store.AdminCreateStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStoreService {

    private final StoreService storeService;

    public PageResponse<AdminStoreResponse> list(Pageable pageable, StoreSortType sortType,
            SortDirection sortDirection) {
        Page<Store> result = storeService.list(pageable, sortType, sortDirection, false);
        PageInfo pageInfo = PageInfo.of(result);
        return PageResponse.of(
                result.getContent().stream().map(AdminStoreResponse::new).collect(Collectors.toList()),
                pageInfo);
    }

    public AdminStoreResponse findById(Long id) {
        Store store = storeService.findById(id);
        return new AdminStoreResponse(store);
    }

    @Transactional
    public Long create(AdminCreateStoreRequest request) {
        return storeService.create(
                request.getName(),
                request.getCity(),
                request.getAddress(),
                request.getCategory(),
                request.getDescription(),
                request.getPriceCategory(),
                request.getEventDescription(),
                request.getContactNumber(),
                request.getDirection(),
                request.getInformation(),
                request.getIsParkingAvailable(),
                request.getIsNewOpen(),
                request.getIsTakeOut());
    }

    // TODO: put -> dynamic update 하도록 수정
    @Transactional
    public void update(Long id, AdminUpdateStoreRequest request) {
        Store store = storeService.findById(id);
        store.updateStoreName(request.getName());
        store.updateCity(request.getCity());
        store.updateAddress(request.getAddress());
        store.updateCategory(request.getCategory());
        store.updateDescription(request.getDescription());
        store.updateContactNumber(request.getContactNumber());
        store.updatePriceCategory(request.getPriceCategory());
        store.updateEventDescription(request.getEventDescription());
        store.updateDirection(request.getDirection());
        store.updateInformation(request.getInformation());
        store.updateParkingAvailability(request.getIsParkingAvailable());
        store.updateIsNewOpen(request.getIsNewOpen());
        store.updateIsTakeOut(request.getIsTakeOut());
    }

    @Transactional
    public void delete(Long id) {
        storeService.delete(id, null);
    }
}