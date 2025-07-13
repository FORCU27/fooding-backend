package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.pointshop.CeoCreatePointShopRequest;
import im.fooding.app.dto.request.ceo.store.pointshop.CeoSearchPointShopRequest;
import im.fooding.app.dto.request.ceo.store.pointshop.CeoUpdatePointShopRequest;
import im.fooding.app.dto.response.ceo.store.CeoPointShopResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.pointshop.PointShopService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStorePointShopService {
    private final PointShopService pointShopService;
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;

    @Transactional
    public Long create(long storeId, CeoCreatePointShopRequest request, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        return pointShopService.create(store, request.getName(), request.getPoint(), request.getProvideType(), request.getConditions(),
                request.getTotalQuantity(), request.getIssueStartOn(), request.getIssueEndOn()).getId();
    }

    @Transactional
    public void update(long storeId, long id, CeoUpdatePointShopRequest request, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        pointShopService.update(id, request.getName(), request.getPoint(), request.getProvideType(), request.getConditions(),
                request.getTotalQuantity(), request.getIssueStartOn(), request.getIssueEndOn());
    }

    @Transactional(readOnly = true)
    public CeoPointShopResponse retrieve(long storeId, long id, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        PointShop pointShop = pointShopService.findById(id);
        return CeoPointShopResponse.of(pointShop);
    }

    @Transactional(readOnly = true)
    public PageResponse<CeoPointShopResponse> list(long storeId, CeoSearchPointShopRequest search, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        Page<PointShop> list = pointShopService.list(store.getId(), search.getIsActive(), null, search.getSearchString(), search.getPageable());
        return PageResponse.of(list.stream().map(CeoPointShopResponse::of).toList(), PageInfo.of(list));
    }

    @Transactional
    public void delete(long storeId, long id, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        pointShopService.delete(id, ceoId);
    }

    @Transactional
    public void active(long storeId, long id, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        pointShopService.active(id);
    }

    @Transactional
    public void inactive(long storeId, long id, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        pointShopService.inactive(id);
   }
}
