package im.fooding.app.service.admin.waiting;


import im.fooding.app.dto.request.admin.waiting.AdminStoreWaitingCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminStoreWaitingUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminStoreWaitingResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingCreateRequest;
import im.fooding.core.dto.request.waiting.StoreWaitingUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingSettingService;
import im.fooding.core.service.waiting.WaitingUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStoreWaitingService {

    private final StoreWaitingService storeWaitingService;
    private final WaitingUserService waitingUserService;
    private final StoreService storeService;
    private final WaitingSettingService waitingSettingService;
    private final UserService userService;

    @Transactional
    public void create(AdminStoreWaitingCreateRequest request) {
        WaitingUser waitingUser = null;
        User user = null;
        if (request.waitingUserId() != null) {
            waitingUser = waitingUserService.get(request.waitingUserId());
        }
        if (request.userId() != null) {
            user = userService.findById(request.userId());
        }
        Store store = storeService.findById(request.storeId());
        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(store);

        validateWaitingOpen(store);
        validateWaitingCondition(waitingSetting, (long) request.adultCount() + request.infantCount());

        StoreWaitingCreateRequest storeWaitingCreateRequest = StoreWaitingCreateRequest.builder()
                .waitingUser(waitingUser)
                .user(user)
                .store(store)
                .status(request.status())
                .channel(request.channel())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .memo(request.memo())
                .build();

        storeWaitingService.create(storeWaitingCreateRequest);
    }

    public AdminStoreWaitingResponse get(long id) {
        return AdminStoreWaitingResponse.from(storeWaitingService.get(id));
    }

    public PageResponse<AdminStoreWaitingResponse> list(BasicSearch search) {
        Page<StoreWaiting> storeWaitings = storeWaitingService.list(search.getPageable());
        return PageResponse.of(storeWaitings.stream().map(AdminStoreWaitingResponse::from).toList(), PageInfo.of(storeWaitings));
    }

    @Transactional
    public void update(long id, AdminStoreWaitingUpdateRequest request) {
        Store store = storeService.findById(request.storeId());
        WaitingUser waitingUser = null;
        User user = null;
        if (request.waitingUserId() != null) {
            waitingUser = waitingUserService.get(request.waitingUserId());
        }
        if (request.userId() != null) {
            user = userService.findById(request.userId());
        }
        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(store);

        validateWaitingOpen(store);
        validateWaitingCondition(waitingSetting, (long) request.adultCount() + request.infantCount());

        StoreWaitingUpdateRequest storeWaitingUpdateRequest = StoreWaitingUpdateRequest.builder()
                .id(id)
                .waitingUser(waitingUser)
                .user(user)
                .store(store)
                .status(request.status())
                .channel(request.channel())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .memo(request.memo())
                .build();

        storeWaitingService.update(storeWaitingUpdateRequest);
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        storeWaitingService.delete(id, deletedBy);
    }

    private void validateWaitingOpen(Store store) {
        if (!waitingSettingService.getActiveSetting(store).isOpen()) {
            throw new ApiException(ErrorCode.WAITING_NOT_OPENED);
        }
    }

    private void validateWaitingCondition(WaitingSetting waitingSetting, Long capacity) {
        if (capacity > waitingSetting.getMaximumCapacity()) {
            throw new ApiException(ErrorCode.STORE_WAITING_EXCEEDS_MAXIMUM_CAPACITY);
        }
        if (capacity < waitingSetting.getMinimumCapacity()) {
            throw new ApiException(ErrorCode.STORE_WAITING_BELOW_MINIMUM_CAPACITY);
        }
    }
}
