package im.fooding.app.service.app.waiting;

import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.app.dto.request.app.waiting.AppWaitingRegisterRequestV3;
import im.fooding.app.dto.response.app.waiting.AppWaitingRegisterResponseV3;
import im.fooding.app.publisher.waiting.StoreWaitingSseEventPublisher;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.event.waiting.StoreWaitingEvent;
import im.fooding.core.event.waiting.StoreWaitingRegisterRequestEvent;
import im.fooding.core.global.kafka.EventProducerService;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppWaitingV3Service {

    private final WaitingUserService waitingUserService;
    private final StoreWaitingService storeWaitingService;
    private final WaitingLogService waitingLogService;
    private final UserNotificationApplicationService userNotificationApplicationService;
    private final WaitingSettingService waitingSettingService;
    private final StoreService storeService;
    private final StoreWaitingSseEventPublisher storeWaitingSseEventPublisher;
    private final EventProducerService eventProducerService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String WAITING_REGISTRATION_TOPIC = "waiting-registration-request";
    private static final String WAITING_STATUS_KEY_PREFIX = "waiting:status:";
    private static final Duration STATUS_TTL = Duration.ofMinutes(10);

    public AppWaitingRegisterResponseV3 register(long storeId, AppWaitingRegisterRequestV3 request) {
        String traceId = UUID.randomUUID().toString();

        StoreWaitingRegisterRequestEvent event = StoreWaitingRegisterRequestEvent.builder()
                .traceId(traceId)
                .storeId(storeId)
                .name(request.name())
                .phoneNumber(request.phoneNumber())
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .termsAgreed(request.termsAgreed())
                .privacyPolicyAgreed(request.privacyPolicyAgreed())
                .thirdPartyAgreed(request.thirdPartyAgreed())
                .marketingConsent(request.marketingConsent())
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .build();

        // Redis에 초기 상태 저장 (QUEUED)
        saveStatus(traceId, "QUEUED", null);

        // Kafka로 이벤트 발행 (Key: storeId)
        eventProducerService.publishEvent(WAITING_REGISTRATION_TOPIC, String.valueOf(storeId), event);

        return new AppWaitingRegisterResponseV3(
                traceId,
                "QUEUED"
        );
    }

    @Transactional
    public void processRegistration(StoreWaitingRegisterRequestEvent event) {
        log.info("앱 웨이팅 등록 이벤트를 처리합니다. traceId={}, storeId={}", event.traceId(), event.storeId());

        try {
            Store store = storeService.findById(event.storeId());
            WaitingSetting waitingSetting = waitingSettingService.getActiveSetting(store);
            storeWaitingService.validate(waitingSetting);

            WaitingUser waitingUser = getOrRegisterUser(event, store);
            StoreWaiting storeWaiting = registerStoreWaiting(event, store, waitingUser);

            waitingLogService.logRegister(storeWaiting);

            if (StringUtils.hasText(event.phoneNumber())) {
                sendNotification(store, storeWaiting, event.phoneNumber());
            }

            storeWaitingSseEventPublisher.publish(
                    new StoreWaitingEvent(
                            event.storeId(),
                            storeWaiting.getId(),
                            StoreWaitingEvent.Type.CREATED
                    )
            );

            // Redis 상태 업데이트 (CREATED)
            saveStatus(event.traceId(), "CREATED", storeWaiting.getId());

            log.info("앱 웨이팅 등록 처리를 완료했습니다. traceId={}, waitingId={}", event.traceId(), storeWaiting.getId());

        } catch (Exception e) {
            log.error("앱 웨이팅 등록 처리 중 오류가 발생했습니다. traceId={}", event.traceId(), e);
            // 실패 상태 저장
            saveStatus(event.traceId(), "FAILED", null);
            throw e;
        }
    }

    public Map<String, Object> getRegistrationStatus(String traceId) {
        String key = WAITING_STATUS_KEY_PREFIX + traceId;
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            throw new IllegalArgumentException("유효하지 않거나 만료된 traceId입니다.");
        }

        try {
            return objectMapper.readValue(value, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("상태 정보 파싱 중 오류가 발생했습니다.", e);
        }
    }

    private void saveStatus(String traceId, String status, Long waitingId) {
        try {
            Map<String, Object> statusMap = new HashMap<>();
            statusMap.put("status", status);
            if (waitingId != null) {
                statusMap.put("waitingId", waitingId);
            }
            String json = objectMapper.writeValueAsString(statusMap);
            redisTemplate.opsForValue().set(WAITING_STATUS_KEY_PREFIX + traceId, json, STATUS_TTL);
        } catch (Exception e) {
            log.error("Redis에 등록 상태 저장 중 오류가 발생했습니다. traceId={}", traceId, e);
        }
    }

    private WaitingUser getOrRegisterUser(StoreWaitingRegisterRequestEvent event, Store store) {
        WaitingUserRegisterRequest waitingUserRegisterRequest = WaitingUserRegisterRequest.builder()
                .store(store)
                .name(event.name())
                .phoneNumber(event.phoneNumber())
                .termsAgreed(event.termsAgreed())
                .privacyPolicyAgreed(event.privacyPolicyAgreed())
                .thirdPartyAgreed(event.thirdPartyAgreed())
                .marketingConsent(event.marketingConsent())
                .build();

        return waitingUserService.getOrElseRegister(waitingUserRegisterRequest);
    }

    private StoreWaiting registerStoreWaiting(StoreWaitingRegisterRequestEvent event, Store store, WaitingUser waitingUser) {
        StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                .waitingUser(waitingUser)
                .store(store)
                .channel(event.channel())
                .infantChairCount(event.infantChairCount())
                .infantCount(event.infantCount())
                .adultCount(event.adultCount())
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
