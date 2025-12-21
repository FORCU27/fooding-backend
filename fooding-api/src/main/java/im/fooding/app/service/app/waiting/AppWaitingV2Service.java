package im.fooding.app.service.app.waiting;

import im.fooding.app.dto.request.app.waiting.AppWaitingRegisterRequestV2;
import im.fooding.app.dto.response.app.waiting.AppWaitingRegisterResponseV2;
import im.fooding.app.publisher.waiting.StoreWaitingSseEventPublisher;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.event.waiting.StoreWaitingEvent;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingSettingService;
import im.fooding.core.service.waiting.WaitingUserService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AppWaitingV2Service {

    private final WaitingUserService waitingUserService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingLogService waitingLogService;
    private final UserNotificationApplicationService userNotificationApplicationService;
    private final WaitingSettingService waitingSettingService;
    private final StoreService storeService;
    private final StoreWaitingSseEventPublisher storeWaitingSseEventPublisher;

    private final RedissonClient redissonClient;

    @Transactional
    public AppWaitingRegisterResponseV2 register(long storeId, AppWaitingRegisterRequestV2 request) {
        String lockKey = "store_waiting:" + storeId;

        return executeWithLock(lockKey, 5, 10, TimeUnit.SECONDS, () -> {
            String phoneNumber = request.phoneNumber();

            Store store = storeService.findById(storeId);
            WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(store);
            storeWaitingService.validate(waitingSetting);

            WaitingUser waitingUser = getOrRegisterUser(request, phoneNumber, store);
            StoreWaiting storeWaiting = registerStoreWaiting(request, store, waitingUser);

            waitingLogService.logRegister(storeWaiting);

            if (StringUtils.hasText(phoneNumber)) {
                sendNotification(store, storeWaiting, phoneNumber);
            }

            long callNumber = storeWaiting.getCallNumber();
            long waitingTurn = storeWaitingService.getWaitingCount(store);
            long expectedTimeMinute = waitingSetting.getEstimatedWaitingTimeMinutes() * waitingTurn;
            long recentEntryTimeMinute = 5; // todo: 최근 입장 시간 구현 필요

            storeWaitingSseEventPublisher.publish(
                new StoreWaitingEvent(
                    storeId,
                    storeWaiting.getId(),
                    StoreWaitingEvent.Type.CREATED
                )
            );

            return new AppWaitingRegisterResponseV2(
                callNumber,
                waitingTurn,
                expectedTimeMinute,
                recentEntryTimeMinute
            );
        });
    }

    private <T> T executeWithLock(String lockKey, long waitTime, long leaseTime, TimeUnit timeUnit, Supplier<T> business) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean isLocked = lock.tryLock(waitTime, leaseTime, timeUnit);
            if (!isLocked) {
                log.warn("분산락 획득 실패. key={}", lockKey);
                throw new IllegalStateException("현재 다른 요청을 처리하고 있습니다. 잠시 후 다시 시도해주세요.");
            }
            log.debug("분산락 획득. key={}", lockKey);
            return business.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("작업 처리 중 오류가 발생했습니다.", e);
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.debug("분산락 해제. key={}", lockKey);
            }
        }
    }

    private WaitingUser getOrRegisterUser(AppWaitingRegisterRequestV2 request, String phoneNumber, Store store) {
        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
            .store(store)
            .name(request.name())
            .phoneNumber(phoneNumber)
            .termsAgreed(request.termsAgreed())
            .privacyPolicyAgreed(request.privacyPolicyAgreed())
            .thirdPartyAgreed(request.thirdPartyAgreed())
            .marketingConsent(request.marketingConsent())
            .build();

        return waitingUserService.getOrElseRegister(waitingUserRegisterRequest);
    }

    private StoreWaiting registerStoreWaiting(AppWaitingRegisterRequestV2 request, Store store, WaitingUser waitingUser) {
        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
            .waitingUser(waitingUser)
            .store(store)
            .channel(StoreWaitingChannel.IN_PERSON.getValue())
            .infantChairCount(request.infantChairCount())
            .infantCount(request.infantCount())
            .adultCount(request.adultCount())
            .build();

        return storeWaitingService.register(storeWaitingRegisterRequest);
    }

    private void sendNotification(Store store, StoreWaiting storeWaiting, String phoneNumber) {
        int order = storeWaitingService.getOrder(storeWaiting.getId());
        int personnel = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        userNotificationApplicationService.sendSmsWaitingRegisterMessage(
            store.getName(),
            personnel,
            order,
            storeWaiting.getCallNumber(),
            phoneNumber
        );
    }
}
