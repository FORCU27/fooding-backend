package im.fooding.app.service.admin.waiting;


import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingSettingResponse;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingListRequest;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.WaitingSettingCreateRequest;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.store.StoreServiceService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminWaitingSettingService {

    private final WaitingSettingService waitingSettingService;
    private final StoreServiceService storeServiceService;

    @Transactional
    public void create(AdminWaitingSettingCreateRequest request) {
        StoreService storeService = storeServiceService.findById(request.storeServiceId());

        WaitingSettingCreateRequest waitingSettingCreateRequest = request.toWaitingSettingCreateRequest(storeService);
        waitingSettingService.create(waitingSettingCreateRequest);
    }

    public AdminWaitingSettingResponse get(long settingId) {
        return AdminWaitingSettingResponse.from(waitingSettingService.get(settingId));
    }

    public PageResponse<AdminWaitingSettingResponse> list(AdminWaitingSettingListRequest search) {
        Page<WaitingSetting> waitingSettings = waitingSettingService.list(search.getStoreServiceId(), search.getIsActive(), search.getPageable());
        return PageResponse.of(waitingSettings.getContent().stream().map(AdminWaitingSettingResponse::from).toList(), PageInfo.of(waitingSettings));
    }

    @Transactional
    public void update(long id, AdminWaitingSettingUpdateRequest request) {
        StoreService storeService = storeServiceService.findById(request.storeServiceId());
        waitingSettingService.update(request.toWaitingSettingUpdateRequest(id, storeService));
    }

    @Transactional
    public void delete(long setting, long deletedBy) {
        waitingSettingService.delete(setting, deletedBy);
    }
}
