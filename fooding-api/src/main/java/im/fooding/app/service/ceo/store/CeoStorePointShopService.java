package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.pointshop.CeoCreatePointShopRequest;
import im.fooding.app.dto.request.ceo.store.pointshop.CeoSearchPointShopRequest;
import im.fooding.app.dto.request.ceo.store.pointshop.CeoUpdatePointShopRequest;
import im.fooding.app.dto.response.ceo.store.CeoPointShopResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.coupon.UserCouponStatus;
import im.fooding.core.model.file.File;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.pointshop.PointShopService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStorePointShopService {
    private final PointShopService pointShopService;
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final FileUploadService fileUploadService;
    private final UserCouponService userCouponService;

    @Transactional
    public Long create(long storeId, CeoCreatePointShopRequest request, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        File image = null;
        if (StringUtils.hasText(request.getImageId())) {
            image = fileUploadService.commit(request.getImageId());
        }
        return pointShopService.create(store, request.getName(), request.getPoint(), request.getProvideType(), request.getConditions(),
                request.getTotalQuantity(), request.getIssueStartOn(), request.getIssueEndOn(), image).getId();
    }

    @Transactional
    public void update(long storeId, long id, CeoUpdatePointShopRequest request, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        PointShop pointShop = pointShopService.findById(id);

        File image = pointShop.getImage();
        String newImageId = request.getImageId();
        if (StringUtils.hasText(newImageId)) {
            boolean isDifferentImage = (image == null) || !newImageId.equals(image.getId());
            if (isDifferentImage) {
                image = fileUploadService.commit(newImageId);
            }
        } else {
            image = null;
        }

        pointShopService.update(pointShop, request.getName(), request.getPoint(), request.getProvideType(), request.getConditions(),
                request.getTotalQuantity(), request.getIssueStartOn(), request.getIssueEndOn(), image);
    }

    @Transactional(readOnly = true)
    public CeoPointShopResponse retrieve(long storeId, long id, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        PointShop pointShop = pointShopService.findById(id);
        Integer usedCount = (int) userCouponService.findByPointShopId(pointShop.getId()).stream()
                .filter(it -> it.getStatus() != UserCouponStatus.AVAILABLE)
                .count();
        return CeoPointShopResponse.of(pointShop, usedCount);
    }

    @Transactional(readOnly = true)
    public PageResponse<CeoPointShopResponse> list(long storeId, CeoSearchPointShopRequest search, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        Page<PointShop> list = pointShopService.list(store.getId(), search.getIsActive(), null, search.getSortType(), search.getSearchString(), search.getPageable());
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
