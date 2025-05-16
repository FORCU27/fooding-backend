package im.fooding.app.service.pos.waiting;

import im.fooding.app.dto.request.pos.waiting.PosUpdateWaitingContactInfoRequest;
import im.fooding.app.dto.request.pos.waiting.PosWaitingListRequest;
import im.fooding.app.dto.response.pos.waiting.PosStoreWaitingResponse;
import im.fooding.app.dto.response.pos.waiting.PosWaitingLogResponse;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.model.waiting.*;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingService;
import im.fooding.core.service.waiting.WaitingSettingService;
import im.fooding.core.service.waiting.WaitingUserService;
import im.fooding.core.common.BasicSearch;
import im.fooding.app.dto.request.pos.waiting.PosWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.app.dto.request.pos.waiting.PosWaitingOccupancyUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PosWaitingService {

    private final UserNotificationApplicationService userNotificationApplicationService;
    private final WaitingService waitingService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingSettingService waitingSettingService;
    private final WaitingUserService waitingUserService;
    private final WaitingLogService waitingLogService;

    public PosStoreWaitingResponse details(long id) {
        return PosStoreWaitingResponse.from(storeWaitingService.get(id));
    }

    public PageResponse<PosStoreWaitingResponse> list(long id, PosWaitingListRequest request) {

        Waiting waiting = waitingService.get(id);

        StoreWaitingFilter storeWaitingFilter = StoreWaitingFilter.builder()
                .storeId(waiting.getStore().getId())
                .status(StoreWaitingStatus.of(request.status()))
                .build();
        Page<StoreWaiting> storeWaitings = storeWaitingService.list(storeWaitingFilter, request.pageable());

        List<PosStoreWaitingResponse> list = storeWaitings.getContent()
                .stream()
                .map(PosStoreWaitingResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(storeWaitings));
    }

    @Transactional
    public void seat(long requestId) {
        storeWaitingService.seat(requestId);
    }

    @Transactional
    public void cancel(long requestId, String reason) {
        StoreWaiting canceledWaiting = storeWaitingService.cancel(requestId);
        userNotificationApplicationService.sendWaitingCancelMessage(canceledWaiting.getStoreName(), reason);
    }

    @Transactional
    public void call(long requestId) {
        StoreWaiting storeWaiting = storeWaitingService.call(requestId);

        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(storeWaiting.getStore());

        userNotificationApplicationService.sendWaitingCallMessage(
                storeWaiting.getStoreName(),
                storeWaiting.getCallNumber(),
                waitingSetting.getEntryTimeLimitMinutes()
        );
    }

    public void revert(long requestId) {
        storeWaitingService.revert(requestId);
    }

    @Transactional
    public void updateContactInfo(long requestId, PosUpdateWaitingContactInfoRequest request) {
        StoreWaiting storeWaiting = storeWaitingService.get(requestId);

        WaitingUser user = storeWaiting.getUser();
        if (user != null) {
            user.updateName(request.name());
            user.updatePhoneNumber(request.phoneNumber());
            return;
        }

        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
                .store(storeWaiting.getStore())
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .termsAgreed(true)
                .privacyPolicyAgreed(true)
                .thirdPartyAgreed(true)
                .marketingConsent(false)
                .build();
        user = waitingUserService.register(waitingUserRegisterRequest);
        storeWaiting.injectUser(user);
    }

    public void register(long id, PosWaitingRegisterRequest request) {
        Waiting waiting = waitingService.get(id);
        storeWaitingService.validate(waiting);

        String name = request.name();
        String phoneNumber = request.phoneNumber();

        WaitingUser waitingUser = null;
        if ((name != null && !name.isBlank())
                || (phoneNumber != null && !phoneNumber.isBlank())
        ) {
            waitingUser = getOrRegisterUser(request, phoneNumber, waiting);
        }

        StoreWaiting storeWaiting = registerStoreWaiting(request, waiting, waitingUser);

        waitingLogService.logRegister(storeWaiting);

        sendNotification(waiting, storeWaiting);
    }

    private WaitingUser getOrRegisterUser(PosWaitingRegisterRequest request, String phoneNumber, Waiting waiting) {
        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
                .store(waiting.getStore())
                .name(request.name())
                .phoneNumber(phoneNumber)
                .termsAgreed(request.termsAgreed())
                .privacyPolicyAgreed(request.privacyPolicyAgreed())
                .thirdPartyAgreed(request.thirdPartyAgreed())
                .marketingConsent(false)
                .build();

        return waitingUserService.getOrElseRegister(waitingUserRegisterRequest);
    }

    private StoreWaiting registerStoreWaiting(PosWaitingRegisterRequest request, Waiting waiting, WaitingUser waitingUser) {
        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                .user(waitingUser)
                .store(waiting.getStore())
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .build();

        return storeWaitingService.register(storeWaitingRegisterRequest);
    }

    private void sendNotification(Waiting waiting, StoreWaiting storeWaiting) {
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        Store store = waiting.getStore();
        userNotificationApplicationService.sendWaitingRegisterMessage(
                store.getName(),
                personnel,
                order,
                storeWaiting.getCallNumber()
        );
    }
    @Transactional
    public void updateWaitingStatus(long id, String statusValue) {
        Waiting waiting = waitingService.get(id);
        WaitingStatus updatedStatus = WaitingStatus.of(statusValue);

        validateUpdateWaitingStatus(waiting, updatedStatus);

        waitingService.updateStatus(id, updatedStatus);
    }

    private void validateUpdateWaitingStatus(Waiting waiting, WaitingStatus updatedStatus) {
        if (!waiting.isOpen()
                && updatedStatus == WaitingStatus.WAITING_OPEN
                && storeWaitingService.exists(waiting.getStore(), StoreWaitingStatus.WAITING)
        ) {
            throw new ApiException(ErrorCode.WAITING_STATUS_STORE_WAITING_EXIST);
        }
    }

    public PageResponse<PosWaitingLogResponse> listLogs(long requestId, BasicSearch search) {
        Page<WaitingLog> logs = waitingLogService.list(requestId, search.getPageable());

        List<PosWaitingLogResponse> list = logs.getContent()
                .stream()
                .map(PosWaitingLogResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(logs));
    }

    @Transactional
    public void updateMemo(long requestId, String memo) {
        storeWaitingService.get(requestId).updateMemo(memo);
    }

    @Transactional
    public void updateOccupancy(long requestId, PosWaitingOccupancyUpdateRequest request) {
        StoreWaiting storeWaiting = storeWaitingService.get(requestId);
        storeWaiting.updateOccupancy(
                request.adultCount(),
                request.infantCount(),
                request.infantChairCount()
        );
    }

    @Transactional
    public void updateWaitingTime(long id, int estimatedWaitingTimeMinutes) {
        Waiting waiting = waitingService.get(id);
        WaitingSetting activeSetting = waitingSettingService.getActiveSetting(waiting.getStore());

        activeSetting.updateWaitingTimeMinutes(estimatedWaitingTimeMinutes);
    }
}
