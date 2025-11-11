package im.fooding.app.service.admin.storeNotification;

import im.fooding.app.dto.request.admin.storeNotification.AdminStoreNotificationCreateRequest;
import im.fooding.app.dto.request.admin.storeNotification.AdminStoreNotificationPageRequest;
import im.fooding.app.dto.response.admin.storeNotification.AdminStoreNotificationResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreNotification;
import im.fooding.core.service.store.StoreNotificationService;
import im.fooding.core.service.store.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStoreNotificationService {

    private final StoreNotificationService storeNotificationService;
    private final StoreService storeService;

    public PageResponse<AdminStoreNotificationResponse> page(AdminStoreNotificationPageRequest request) {
        Page<StoreNotification> storeNotifications = storeNotificationService.list(request.getPageable());

        return PageResponse.of(
                storeNotifications.map(AdminStoreNotificationResponse::from).toList(),
                PageInfo.of(storeNotifications)
        );
    }

    @Transactional
    public Long create(@Valid AdminStoreNotificationCreateRequest request) {
        Store store = storeService.findById(request.getStoreId());

        return storeNotificationService.create(
                store,
                request.getTitle(),
                request.getContent(),
                request.getCategory(),
                request.getLinkUrl()
        );
    }
}
