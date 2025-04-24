package im.fooding.app.service.waiting;

import im.fooding.app.dto.request.waiting.PosWaitingRegisterRequest;
import im.fooding.app.dto.request.waiting.WaitingListRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.*;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingService;
import im.fooding.core.service.waiting.WaitingSettingService;
import im.fooding.core.service.waiting.WaitingUserService;
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
    private final WaitingLogService waitingLogService;
    private final WaitingUserService waitingUserService;

    public PageResponse<WaitingResponse> list(long id, WaitingListRequest request) {

        Waiting waiting = waitingService.getById(id);

        StoreWaitingFilter storeWaitingFilter = StoreWaitingFilter.builder()
                .storeId(waiting.getStore().getId())
                .status(StoreWaitingStatus.of(request.status()))
                .build();
        Page<StoreWaiting> storeWaitings = storeWaitingService.list(storeWaitingFilter, request.pageable());

        List<WaitingResponse> list = storeWaitings.getContent()
                .stream()
                .map(WaitingResponse::from)
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
    public void register(long id, PosWaitingRegisterRequest request) {
        Waiting waiting = waitingService.getById(id);
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
}
