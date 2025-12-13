package im.fooding.app.service.pos.waiting;

import im.fooding.app.dto.request.pos.waiting.PosUpdateWaitingContactInfoRequest;
import im.fooding.app.dto.request.pos.waiting.PosWaitingListRequest;
import im.fooding.app.dto.response.pos.waiting.PosStoreWaitingResponse;
import im.fooding.app.dto.response.pos.waiting.PosWaitingLogResponse;
import im.fooding.app.publisher.waiting.StoreWaitingSseEventPublisher;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.event.waiting.StoreWaitingCallEvent;
import im.fooding.core.event.waiting.StoreWaitingCanceledEvent;
import im.fooding.core.event.waiting.StoreWaitingEvent;
import im.fooding.core.event.waiting.StoreWaitingRegisteredEvent;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.model.waiting.*;
import im.fooding.core.service.plan.PlanService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingNumberGeneratorService;
import im.fooding.core.service.waiting.WaitingSettingService;
import im.fooding.core.service.waiting.WaitingUserService;
import im.fooding.core.common.BasicSearch;
import im.fooding.app.dto.request.pos.waiting.PosWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
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
    private final StoreWaitingService storeWaitingService;
    private final WaitingSettingService waitingSettingService;
    private final WaitingUserService waitingUserService;
    private final WaitingLogService waitingLogService;
    private final UserService userService;
    private final PlanService planService;
    private final EventProducerService eventProducerService;
    private final StoreService storeService;
    private final WaitingNumberGeneratorService waitingNumberGeneratorService;
    private final StoreWaitingSseEventPublisher storeWaitingSseEventPublisher;

    public PosStoreWaitingResponse details(long id) {
        return PosStoreWaitingResponse.from(storeWaitingService.get(id));
    }

    public PageResponse<PosStoreWaitingResponse> list(long storeId, PosWaitingListRequest request) {
        StoreWaitingFilter storeWaitingFilter = StoreWaitingFilter.builder()
                .storeId(storeId)
                .status(request.getStatus())
                .build();

        Page<StoreWaiting> storeWaitings = storeWaitingService.list(storeWaitingFilter, request.getPageable());

        List<PosStoreWaitingResponse> list = storeWaitings.getContent()
                .stream()
                .map(PosStoreWaitingResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(storeWaitings));
    }

    @Transactional
    public void seat(long storeId, long storeWaitingId) {
        storeWaitingService.seat(storeWaitingId);

        storeWaitingSseEventPublisher.publish(
                new StoreWaitingEvent(
                        storeId,
                        storeWaitingId,
                        StoreWaitingEvent.Type.UPDATED
                )
        );
    }

    @Transactional
    public void cancel(long storeId, long storeWaitingId, String reason) {
        storeWaitingService.cancel(storeWaitingId);

        eventProducerService.publishEvent(
                StoreWaitingCanceledEvent.class.getSimpleName(),
                new StoreWaitingCanceledEvent(storeWaitingId, reason)
        );
        storeWaitingSseEventPublisher.publish(
                new StoreWaitingEvent(
                        storeId,
                        storeWaitingId,
                        StoreWaitingEvent.Type.UPDATED
                )
        );
    }

    @Transactional
    public void call(long storeId, long storeWaitingId) {
        storeWaitingService.call(storeWaitingId);

        eventProducerService.publishEvent(
                StoreWaitingCallEvent.class.getSimpleName(),
                new StoreWaitingCallEvent(storeWaitingId)
        );
        storeWaitingSseEventPublisher.publish(
                new StoreWaitingEvent(
                        storeId,
                        storeWaitingId,
                        StoreWaitingEvent.Type.UPDATED
                )
        );
    }

    public void revert(long storeWaitingId) {
        storeWaitingService.revert(storeWaitingId);
    }

    @Transactional
    public void updateContactInfo(long storeId, long storeWaitingId, PosUpdateWaitingContactInfoRequest request) {
        StoreWaiting storeWaiting = storeWaitingService.get(storeWaitingId);

        WaitingUser user = storeWaiting.getWaitingUser();
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

        storeWaitingSseEventPublisher.publish(
                new StoreWaitingEvent(
                        storeId,
                        storeWaitingId,
                        StoreWaitingEvent.Type.UPDATED
                )
        );
    }

    @Transactional
    public void register(long storeId, PosWaitingRegisterRequest request) {
        Store store = storeService.findById(storeId);
        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(store);
        storeWaitingService.validate(waitingSetting);

        String name = request.name();
        String phoneNumber = request.phoneNumber();

        User user = userService.findOptionalByPhoneNumber(phoneNumber)
                .orElse(null);
        WaitingUser waitingUser = null;
        if ((name != null && !name.isBlank())
                || (phoneNumber != null && !phoneNumber.isBlank())
        ) {
            waitingUser = getOrRegisterUser(request, phoneNumber, waitingSetting);
        }

        StoreWaiting storeWaiting = registerStoreWaiting(request, waitingSetting, user, waitingUser);

        waitingLogService.logRegister(storeWaiting);

        if (storeWaiting.getUser() != null) {
            planService.create(storeWaiting);
        }

        eventProducerService.publishEvent(
                StoreWaitingRegisteredEvent.class.getSimpleName(),
                new StoreWaitingRegisteredEvent(storeWaiting.getId())
        );
        storeWaitingSseEventPublisher.publish(
                new StoreWaitingEvent(
                        storeId,
                        storeWaiting.getId(),
                        StoreWaitingEvent.Type.CREATED
                )
        );
    }

    private WaitingUser getOrRegisterUser(PosWaitingRegisterRequest request, String phoneNumber, WaitingSetting waitingSetting) {
        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
                .store(waitingSetting.getStoreService().getStore())
                .name(request.name())
                .phoneNumber(phoneNumber)
                .termsAgreed(request.termsAgreed())
                .privacyPolicyAgreed(request.privacyPolicyAgreed())
                .thirdPartyAgreed(request.thirdPartyAgreed())
                .marketingConsent(false)
                .build();

        return waitingUserService.getOrElseRegister(waitingUserRegisterRequest);
    }

    private StoreWaiting registerStoreWaiting(PosWaitingRegisterRequest request, WaitingSetting waitingSetting, User user, WaitingUser waitingUser) {
        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                .user(user)
                .waitingUser(waitingUser)
                .store(waitingSetting.getStoreService().getStore())
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .build();

        return storeWaitingService.register(storeWaitingRegisterRequest);
    }

    @Transactional
    public void updateWaitingStatus(long waitingSettingId, String statusValue) {
        WaitingSetting waitingSetting = waitingSettingService.get(waitingSettingId);
        WaitingStatus updatedStatus = WaitingStatus.of(statusValue);

        validateUpdateWaitingStatus(waitingSetting, updatedStatus);

        waitingSettingService.updateStatus(waitingSettingId, updatedStatus);
    }

    private void validateUpdateWaitingStatus(WaitingSetting waitingSetting, WaitingStatus updatedStatus) {
        if (!waitingSetting.isOpen()
                && updatedStatus == WaitingStatus.WAITING_CLOSE
                && storeWaitingService.exists(waitingSetting.getStoreService().getStore(), StoreWaitingStatus.WAITING)
        ) {
            throw new ApiException(ErrorCode.WAITING_STATUS_STORE_WAITING_EXIST);
        }
    }

    public PageResponse<PosWaitingLogResponse> listLogs(long storeWaitingId, BasicSearch search) {
        Page<WaitingLog> logs = waitingLogService.list(storeWaitingId, search.getPageable());

        List<PosWaitingLogResponse> list = logs.getContent()
                .stream()
                .map(PosWaitingLogResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(logs));
    }

    @Transactional
    public void updateMemo(long storeWaitingId, String memo) {
        storeWaitingService.get(storeWaitingId).updateMemo(memo);
    }

    @Transactional
    public void updateOccupancy(long storeWaitingId, PosWaitingOccupancyUpdateRequest request) {
        StoreWaiting storeWaiting = storeWaitingService.get(storeWaitingId);
        storeWaiting.updateOccupancy(
                request.adultCount(),
                request.infantCount(),
                request.infantChairCount()
        );
    }

    @Transactional
    public void updateWaitingTime(long waitingSettingId, int estimatedWaitingTimeMinutes) {
        WaitingSetting activeSetting = waitingSettingService.get(waitingSettingId);

        activeSetting.updateWaitingTimeMinutes(estimatedWaitingTimeMinutes);
    }

    @Transactional
    public void updateWaitingSettingActive(long waitingSettingId, boolean active) {
        waitingSettingService.updateActive(waitingSettingId, active);
    }

    @Transactional
    public void resetWaitingCallNumber(long storeId) {
        waitingNumberGeneratorService.resetNumber(storeId);
    }
}
