package im.fooding.app.service.app.waiting;

import im.fooding.app.dto.request.app.waiting.AppWaitingListRequest;
import im.fooding.app.dto.request.app.waiting.AppWaitingRegisterRequest;
import im.fooding.app.dto.response.app.waiting.*;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingService;
import im.fooding.core.service.waiting.WaitingSettingService;
import im.fooding.core.service.waiting.WaitingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AppWaitingApplicationService {

    private final WaitingService waitingService;
    private final WaitingUserService waitingUserService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingLogService waitingLogService;
    private final UserNotificationApplicationService userNotificationApplicationService;
    private final WaitingSettingService waitingSettingService;
    private final StoreService storeService;

    public AppStoreWaitingResponse details(long id) {
        return AppStoreWaitingResponse.from(storeWaitingService.get(id));
    }

    public PageResponse<AppStoreWaitingResponse> list(long storeId, AppWaitingListRequest request) {
        StoreWaitingFilter storeWaitingFilter = StoreWaitingFilter.builder()
                .storeId(storeId)
                .status(StoreWaitingStatus.of(request.getStatus()))
                .build();
        Page<StoreWaiting> storeWaitings = storeWaitingService.list(storeWaitingFilter, request.getPageable());

        List<AppStoreWaitingResponse> list = storeWaitings.getContent()
                .stream()
                .map(AppStoreWaitingResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(storeWaitings));
    }

    @Transactional
    public AppWaitingRegisterResponse register(long storeId, AppWaitingRegisterRequest request) {
        String phoneNumber = request.phoneNumber();

        Store store = storeService.findById(storeId);
        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(store);
        Waiting waiting = waitingSetting.getWaiting();
        storeWaitingService.validate(waiting);

        WaitingUser waitingUser = getOrRegisterUser(request, phoneNumber, waiting);
        StoreWaiting storeWaiting = registerStoreWaiting(request, waiting, waitingUser);

        waitingLogService.logRegister(storeWaiting);

        if (StringUtils.hasText(phoneNumber)) {
            sendNotification(waiting, storeWaiting, phoneNumber);
        }

        long callNumber = storeWaiting.getCallNumber();
        long waitingTurn = storeWaitingService.getWaitingCount(store);
        long expectedTimeMinute = waitingSetting.getEstimatedWaitingTimeMinutes() * waitingTurn;
        long recentEntryTimeMinute = 5; // todo: 최근 입장 시간 구현 필요

        return new AppWaitingRegisterResponse(
                callNumber,
                waitingTurn,
                expectedTimeMinute,
                recentEntryTimeMinute
        );
    }

    private WaitingUser getOrRegisterUser(AppWaitingRegisterRequest request, String phoneNumber, Waiting waiting) {
        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
                .store(waiting.getStore())
                .name(request.name())
                .phoneNumber(phoneNumber)
                .termsAgreed(request.termsAgreed())
                .privacyPolicyAgreed(request.privacyPolicyAgreed())
                .thirdPartyAgreed(request.thirdPartyAgreed())
                .marketingConsent(request.marketingConsent())
                .build();

        return waitingUserService.getOrElseRegister(waitingUserRegisterRequest);
    }

    private StoreWaiting registerStoreWaiting(AppWaitingRegisterRequest request, Waiting waiting, WaitingUser waitingUser) {
        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                .waitingUser(waitingUser)
                .store(waiting.getStore())
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .build();

        return storeWaitingService.register(storeWaitingRegisterRequest);
    }

    private void sendNotification(Waiting waiting, StoreWaiting storeWaiting, String phoneNumber) {
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        Store store = waiting.getStore();
        userNotificationApplicationService.sendSmsWaitingRegisterMessage(
                store.getName(),
                personnel,
                order,
                storeWaiting.getCallNumber(),
                phoneNumber
        );
    }

    public PageResponse<AppWaitingLogResponse> listLogs(long requestId, BasicSearch search) {
        Page<WaitingLog> logs = waitingLogService.list(requestId, search.getPageable());

        List<AppWaitingLogResponse> list = logs.getContent()
                .stream()
                .map(AppWaitingLogResponse::from)
                .toList();

        return PageResponse.of(list, PageInfo.of(logs));
    }

    public AppWaitingOverviewResponse overviewRequests(long storeId) {
        Store store = storeService.findById(storeId);
        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(store);

        int waitingCount = (int) storeWaitingService.getWaitingCount(store);
        int estimatedWaitingTimeMinutes = waitingSetting.getEstimatedWaitingTimeMinutes() * waitingCount;

        return new AppWaitingOverviewResponse(waitingCount, estimatedWaitingTimeMinutes);
    }

    public List<AppWaitingStatusResponse> waitingStatus( long storeId ){
        StoreWaitingFilter filter = StoreWaitingFilter.builder()
                .storeId( storeId )
                .status( StoreWaitingStatus.WAITING )
                .build();
        Pageable pageable = PageRequest.of( 0, Integer.MAX_VALUE );
        Page<StoreWaiting> response = storeWaitingService.list( filter, pageable );
        return response.map( AppWaitingStatusResponse::of ).stream().toList();
    }
}
