package im.fooding.app.service.admin.waiting;


import im.fooding.app.dto.request.admin.waiting.AdminWaitingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingSettingUpdateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUpdateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUserCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUserUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingResponse;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingSettingResponse;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingUserResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.WaitingSettingCreateRequest;
import im.fooding.core.dto.request.waiting.WaitingSettingUpdateRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.waiting.WaitingService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingSettingService;
import im.fooding.core.service.waiting.WaitingUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminWaitingService {

    private final WaitingService waitingService;
    private final WaitingSettingService waitingSettingService;
    private final WaitingUserService waitingUserService;
    private final StoreService storeService;

    @Transactional
    public AdminWaitingResponse createWaiting(AdminWaitingCreateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingStatus status = WaitingStatus.of(request.status());

        Waiting waiting = waitingService.create(store, status);

        return AdminWaitingResponse.from(waiting);
    }

    public AdminWaitingResponse getWaiting(long id) {
        return AdminWaitingResponse.from(waitingService.getById(id));
    }

    public PageResponse<AdminWaitingResponse> getWaitingList(BasicSearch search) {
        Page<Waiting> waitings = waitingService.getList(search.getPageable());
        return PageResponse.of(waitings.stream().map(AdminWaitingResponse::from).toList(), PageInfo.of(waitings));
    }

    @Transactional
    public AdminWaitingResponse updateWaiting(long id, AdminWaitingUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingStatus status = WaitingStatus.of(request.status());

        return AdminWaitingResponse.from(waitingService.update(id, store, status));
    }

    @Transactional
    public void deleteWaiting(long id, long deletedBy) {
        waitingService.delete(id, deletedBy);
    }

    @Transactional
    public AdminWaitingSettingResponse createWaitingSetting(AdminWaitingSettingCreateRequest request) {
        Waiting waiting = waitingService.getById(request.waitingId());

        WaitingSettingCreateRequest waitingSettingCreateRequest = request.toWaitingSettingCreateRequest(waiting);
        WaitingSetting waitingSetting = waitingSettingService.create(waitingSettingCreateRequest);

        return AdminWaitingSettingResponse.from(waitingSetting);
    }

    public AdminWaitingSettingResponse getWaitingSetting(long settingId) {
        return AdminWaitingSettingResponse.from(waitingSettingService.get(settingId));
    }

    public PageResponse<AdminWaitingSettingResponse> getWaitingSettingList(BasicSearch search) {
        Page<WaitingSetting> waitingSettings = waitingSettingService.getList(search.getPageable());
        List<AdminWaitingSettingResponse> waitingResponses = waitingSettings.stream()
                .map(AdminWaitingSettingResponse::from)
                .toList();

        return PageResponse.of(waitingResponses, PageInfo.of(waitingSettings));
    }

    @Transactional
    public AdminWaitingSettingResponse updateWaitingSetting(long settingId, AdminWaitingSettingUpdateRequest request) {
        Waiting waiting = waitingService.getById(request.waitingId());

        WaitingSettingUpdateRequest waitingSettingUpdateRequest = request.toWaitingSettingUpdateRequest(settingId, waiting);
        WaitingSetting waitingSetting = waitingSettingService.update(waitingSettingUpdateRequest);

        return AdminWaitingSettingResponse.from(waitingSetting);
    }

    @Transactional
    public void deleteWaitingSetting(long setting, long deletedBy) {
        waitingSettingService.delete(setting, deletedBy);
    }

    @Transactional
    public AdminWaitingUserResponse createWaitingUser(AdminWaitingUserCreateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingUser waitingUser = waitingUserService.create(request.toWaitingUserCreateRequest(store));
        return AdminWaitingUserResponse.from(waitingUser);
    }

    public AdminWaitingUserResponse getWaitingUser(long userId) {
        return AdminWaitingUserResponse.from(waitingUserService.get(userId));
    }

    public PageResponse<AdminWaitingUserResponse> getWaitingUserList(BasicSearch search) {
        Page<WaitingUser> waitingUsers = waitingUserService.getList(search.getPageable());
        return PageResponse.of(waitingUsers.stream().map(AdminWaitingUserResponse::from).toList(), PageInfo.of(waitingUsers));
    }

    @Transactional
    public AdminWaitingUserResponse updateWaitingUser(long userId, AdminWaitingUserUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        return AdminWaitingUserResponse.from(waitingUserService.update(request.toWaitingUserUpdateRequest(userId, store)));
    }

    @Transactional
    public void deleteWaitingUser(long userId, long deletedBy) {
        waitingUserService.delete(userId, deletedBy);
    }
}
