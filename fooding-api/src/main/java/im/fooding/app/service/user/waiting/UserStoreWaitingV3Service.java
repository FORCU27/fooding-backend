package im.fooding.app.service.user.waiting;

import com.fasterxml.jackson.databind.ObjectMapper;
import im.fooding.app.dto.request.user.waiting.UserStoreWaitingRegisterRequestV3;
import im.fooding.app.dto.response.user.waiting.UserStoreWaitingCreateResponseV3;
import im.fooding.app.publisher.waiting.StoreWaitingSseEventPublisher;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.event.waiting.StoreWaitingEvent;
import im.fooding.core.event.waiting.StoreWaitingRegisterRequestEvent;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreWaitingV3Service {

    private final EventProducerService eventProducerService;
    private final StoreServiceService storeServiceService;
    private final WaitingSettingService waitingSettingService;
    private final StoreWaitingService storeWaitingService;
    private final UserService userService;
    private final WaitingLogService waitingLogService;
    private final PlanService planService;
    private final StoreWaitingSseEventPublisher storeWaitingSseEventPublisher;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String WAITING_REGISTRATION_TOPIC = "waiting-registration-request";
    private static final String WAITING_STATUS_KEY_PREFIX = "waiting:status:";
    private static final Duration STATUS_TTL = Duration.ofMinutes(10);

    public UserStoreWaitingCreateResponseV3 registerStoreWaiting(UserStoreWaitingRegisterRequestV3 request, Long userId) {
        String traceId = UUID.randomUUID().toString();

        StoreWaitingRegisterRequestEvent event = StoreWaitingRegisterRequestEvent.builder()
                .traceId(traceId)
                .userId(userId)
                .storeId(request.getStoreId())
                .channel(StoreWaitingChannel.ONLINE.getValue())
                .termsAgreed(request.getTermsAgreed())
                .privacyPolicyAgreed(request.getPrivacyPolicyAgreed())
                .thirdPartyAgreed(request.getThirdPartyAgreed())
                .infantChairCount(request.getInfantChairCount())
                .infantCount(request.getInfantCount())
                .adultCount(request.getAdultCount())
                .build();

        // Redis에 초기 상태 저장 (QUEUED)
        saveStatus(traceId, "QUEUED", null);

        // Kafka로 이벤트 발행 (Key: storeId)
        eventProducerService.publishEvent(WAITING_REGISTRATION_TOPIC, String.valueOf(request.getStoreId()), event);

        return new UserStoreWaitingCreateResponseV3(
                traceId,
                "QUEUED"
        );
    }

    @Transactional
    public void processRegistration(StoreWaitingRegisterRequestEvent event) {
        log.info("웨이팅 등록 이벤트를 처리합니다. traceId={}, storeId={}", event.traceId(), event.storeId());

        try {
            StoreServiceFilter filter = StoreServiceFilter.builder()
                    .storeId(event.storeId())
                    .type(StoreServiceType.WAITING)
                    .build();
            StoreService storeService = storeServiceService.get(filter);
            WaitingSetting waitingSetting = waitingSettingService.getByStoreService(storeService);
            storeWaitingService.validate(waitingSetting);

            User user = userService.findById(event.userId());

            StoreWaitingRegisterRequest storeWaitingRegisterRequest = StoreWaitingRegisterRequest.builder()
                    .user(user)
                    .waitingUser(null)
                    .store(waitingSetting.getStoreService().getStore())
                    .channel(event.channel())
                    .infantChairCount(event.infantChairCount())
                    .infantCount(event.infantCount())
                    .adultCount(event.adultCount())
                    .build();

            StoreWaiting storeWaiting = storeWaitingService.register(storeWaitingRegisterRequest);

            waitingLogService.logRegister(storeWaiting);

            ObjectId planId = planService.create(storeWaiting);

            eventProducerService.publishEvent(
                    StoreWaitingRegisterRequest.class.getSimpleName(),
                    new StoreWaitingRegisteredEvent(storeWaiting.getId())
            );

            // SSE 알림 전송
            storeWaitingSseEventPublisher.publish(
                    new StoreWaitingEvent(
                            event.storeId(),
                            storeWaiting.getId(),
                            StoreWaitingEvent.Type.CREATED
                    )
            );

            // Redis 상태 업데이트 (CREATED)
            saveStatus(event.traceId(), "CREATED", storeWaiting.getId());

            log.info("웨이팅 등록 처리를 완료했습니다. traceId={}, waitingId={}", event.traceId(), storeWaiting.getId());

        } catch (Exception e) {
            log.error("웨이팅 등록 처리 중 오류가 발생했습니다. traceId={}", event.traceId(), e);
            // 실패 상태 저장
            saveStatus(event.traceId(), "FAILED", null);
            throw e; // Consumer에서 DLQ로 보내기 위해 예외 다시 던짐
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
}
