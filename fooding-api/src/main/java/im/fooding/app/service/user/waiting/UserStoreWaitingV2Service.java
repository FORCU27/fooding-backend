package im.fooding.app.service.user.waiting;

import im.fooding.app.dto.request.user.waiting.UserStoreWaitingRegisterRequestV2;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingCreateResponseV2;
import im.fooding.app.publisher.waiting.StoreWaitingSseEventPublisher;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.event.waiting.StoreWaitingEvent;
import im.fooding.core.event.waiting.StoreWaitingRegisteredEvent;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.store.StoreServiceType;
import im.fooding.core.model.user.User;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.repository.store.StoreServiceFilter;
import im.fooding.core.service.plan.PlanService;
import im.fooding.core.service.store.StoreServiceService;
import im.fooding.core.service.user.UserService;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import im.fooding.core.service.waiting.WaitingSettingService;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreWaitingV2Service {

    private final StoreServiceService storeServiceService;
    private final WaitingSettingService waitingSettingService;
    private final StoreWaitingService storeWaitingService;
    private final UserService userService;
    private final WaitingLogService waitingLogService;
    private final PlanService planService;
    private final EventProducerService eventProducerService;
    private final StoreWaitingSseEventPublisher storeWaitingSseEventPublisher;
    private final RedissonClient redissonClient;

    @Transactional
    public UserStoreWaitingCreateResponseV2 registerStoreWaiting(UserStoreWaitingRegisterRequestV2 request, Long userId) {
        String lockKey = "store_waiting:" + request.getStoreId();

        return executeWithLock(lockKey, 5, 10, TimeUnit.SECONDS, () -> {
            StoreServiceFilter filter = StoreServiceFilter.builder()
                    .storeId(request.getStoreId())
                    .type(StoreServiceType.WAITING)
                    .build();
            StoreService storeService = storeServiceService.get(filter);
            WaitingSetting waitingSetting = waitingSettingService.getByStoreService(storeService);
            storeWaitingService.validate(waitingSetting);

            User user = userService.findById(userId);

            StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                    .user(user)
                    .waitingUser(null)
                    .store(waitingSetting.getStoreService().getStore())
                    .channel(StoreWaitingChannel.ONLINE.getValue())
                    .infantChairCount(request.getInfantChairCount())
                    .infantCount(request.getInfantCount())
                    .adultCount(request.getAdultCount())
                    .build();

            StoreWaiting storeWaiting = storeWaitingService.register(storeWaitingRegisterRequest);

            waitingLogService.logRegister(storeWaiting);

            ObjectId planId = planService.create(storeWaiting);

            eventProducerService.publishEvent(
                    StoreWaitingRegisterRequest.class.getSimpleName(),
                    new StoreWaitingRegisteredEvent(storeWaiting.getId())
            );
            storeWaitingSseEventPublisher.publish(
                    new StoreWaitingEvent(
                            request.getStoreId(),
                            storeWaiting.getId(),
                            StoreWaitingEvent.Type.CREATED
                    )
            );

            return new UserStoreWaitingCreateResponseV2(storeWaiting.getId(), planId.toString());
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
}
