package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.pointshop.AdminCreatePointShopRequest;
import im.fooding.app.dto.request.admin.pointshop.AdminSearchPointShopRequest;
import im.fooding.app.dto.request.admin.pointshop.AdminUpdatePointShopRequest;
import im.fooding.app.dto.response.admin.pointshop.AdminPointShopResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.coupon.UserCouponStatus;
import im.fooding.core.model.file.File;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.pointshop.PointShopService;
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
public class AdminStorePointShopService {
    private final PointShopService pointShopService;
    private final StoreService storeService;
    private final FileUploadService fileUploadService;
    private final UserCouponService userCouponService;

    @Transactional
    public Long create(AdminCreatePointShopRequest request) {
        Store store = storeService.findById(request.getStoreId());
        File image = null;
        if (StringUtils.hasText(request.getImageId())) {
            image = fileUploadService.commit(request.getImageId());
        }
        return pointShopService.create(store, request.getName(), request.getPoint(), request.getProvideType(), request.getConditions(),
                request.getTotalQuantity(), request.getIssueStartOn(), request.getIssueEndOn(), image).getId();
    }

    @Transactional
    public void update(long id, AdminUpdatePointShopRequest request) {
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
    public AdminPointShopResponse retrieve(long id) {
        PointShop pointShop = pointShopService.findById(id);
        Integer usedCount = (int) userCouponService.findByPointShopId(pointShop.getId()).stream()
                .filter(it -> it.getStatus() != UserCouponStatus.AVAILABLE)
                .count();
        return AdminPointShopResponse.of(pointShop, usedCount);
    }

    @Transactional(readOnly = true)
    public PageResponse<AdminPointShopResponse> list(AdminSearchPointShopRequest search) {
        Page<PointShop> list = pointShopService.list(search.getStoreId(), search.getIsActive(), null, search.getSortType(), search.getSearchString(), search.getPageable());
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
