package im.fooding.app.service.waiting;

import im.fooding.app.dto.request.waiting.WaitingRegisterServiceRequest;
import im.fooding.app.dto.response.waiting.WaitingRegisterServiceResponse;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.waiting.StoreService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WaitingApplicationService {

    private final StoreService storeService;
    private final WaitingUserService waitingUserService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingLogService waitingLogService;

    public WaitingRegisterServiceResponse register(WaitingRegisterServiceRequest request) {
        long storeId = request.storeId();
        String phoneNumber = request.phoneNumber();

        Store store = storeService.getById(storeId);

        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
                .store(store)
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
                .store(store)
                .channel(request.channel())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .build();
        StoreWaiting storeWaiting = storeWaitingService.register(storeWaitingRegisterRequest);

        waitingLogService.logRegister(storeWaiting);

        return new WaitingRegisterServiceResponse(storeWaiting.getCallNumber());
    }
}
