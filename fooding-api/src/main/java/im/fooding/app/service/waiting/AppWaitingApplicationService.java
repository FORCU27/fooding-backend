package im.fooding.app.service.waiting;

import im.fooding.app.dto.request.waiting.AppWaitingRegisterRequest;
import im.fooding.app.dto.response.waiting.AppWaitingRegisterResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingService;
import im.fooding.core.service.waiting.WaitingUserService;
import im.fooding.app.dto.response.waiting.StoreWaitingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import im.fooding.app.dto.request.waiting.WaitingListRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AppWaitingApplicationService {

    private final WaitingService waitingService;
    private final WaitingUserService waitingUserService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingLogService waitingLogService;

    public StoreWaitingResponse details(long id) {
        return StoreWaitingResponse.from(storeWaitingService.get(id));
    }

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
    public AppWaitingRegisterResponse register(long id, AppWaitingRegisterRequest request) {
        String phoneNumber = request.phoneNumber();

        Waiting waiting = waitingService.getById(id);
        storeWaitingService.validate(waiting);

        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
                .store(waiting.getStore())
                .name(request.name())
                .phoneNumber(phoneNumber)
                .termsAgreed(request.termsAgreed())
                .privacyPolicyAgreed(request.privacyPolicyAgreed())
                .thirdPartyAgreed(request.thirdPartyAgreed())
                .marketingConsent(request.marketingConsent())
                .build();
        WaitingUser waitingUser = waitingUserService.getOrElseRegister(waitingUserRegisterRequest);

        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                .user(waitingUser)
                .store(waiting.getStore())
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .build();
        StoreWaiting storeWaiting = storeWaitingService.register(storeWaitingRegisterRequest);

        waitingLogService.logRegister(storeWaiting);

        return new AppWaitingRegisterResponse(storeWaiting.getCallNumber());
    }
}
