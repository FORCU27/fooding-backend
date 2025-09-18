package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.WaitingSettingCreateRequest;
import im.fooding.core.dto.request.waiting.WaitingSettingUpdateRequest;
import im.fooding.core.event.store.StoreWaitingServiceCreatedEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.kafka.KafkaEventHandler;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.repository.waiting.WaitingSettingFilter;
import im.fooding.core.repository.waiting.WaitingSettingRepository;
import im.fooding.core.service.store.StoreServiceService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingSettingService {

    private final WaitingSettingRepository waitingSettingRepository;
    private final StoreServiceService storeServiceService;

    @Transactional
    public void create(WaitingSettingCreateRequest request) {
        if (request.isActive()) {
            validateAlreadyActive(request.storeService());
        }

        WaitingSetting waitingSetting = WaitingSetting.builder()
                .storeService(request.storeService())
                .label(request.label())
                .minimumCapacity(request.minimumCapacity())
                .maximumCapacity(request.maximumCapacity())
                .estimatedWaitingTimeMinutes(request.estimatedWaitingTimeMinutes())
                .isActive(request.isActive())
                .entryTimeLimitMinutes(request.entryTimeLimitMinutes())
                .status(request.status())
                .build();

        waitingSettingRepository.save(waitingSetting);
    }

    public WaitingSetting get(long id) {
        return waitingSettingRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_SETTING_NOT_FOUND));
    }

    public Page<WaitingSetting> list(Long storeServiceId, Boolean isActive, Pageable pageable) {
        return waitingSettingRepository.list(storeServiceId, isActive, pageable);
    }

    public Page<WaitingSetting> list(Long storeServiceId, WaitingStatus status, Pageable pageable) {
        WaitingSettingFilter filter = WaitingSettingFilter.builder()
                .storeServiceId(storeServiceId)
                .status(status)
                .build();
        return waitingSettingRepository.list(filter, pageable);
    }

    @Transactional
    public void update(WaitingSettingUpdateRequest request) {
        WaitingSetting waitingSetting = get(request.id());
        if (request.isActive() && !waitingSetting.isActive()) {
            validateAlreadyActive(request.storeService());
        }

        waitingSetting.update(
                request.storeService(),
                request.label(),
                request.minimumCapacity(),
                request.maximumCapacity(),
                request.estimatedWaitingTimeMinutes(),
                request.isActive(),
                request.entryTimeLimitMinutes(),
                request.status()
        );
    }

    @Transactional
    public void updateStatus(long id, WaitingStatus status) {
        WaitingSetting waitingSetting = get(id);
        waitingSetting.updateStatus(status);
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        WaitingSetting waitingSetting = get(id);

        waitingSetting.delete(deletedBy);
    }

    public WaitingSetting getActiveSetting(Store store) {
        return waitingSettingRepository.findActive(store)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.ACTIVE_WAITING_SETTING_NOT_FOUND));
    }

    public WaitingSetting getByStoreService(StoreService storeService) {
        return waitingSettingRepository.findByStoreService(storeService)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.ACTIVE_WAITING_SETTING_NOT_FOUND));
    }

    /**
     * 가게의 대기 시간을 조회합니다.
     *  위에 있는 경우 active가 false일 때 예외가 발생하는 문제로 메서드 Optional로 감싼 메서드를 추가했습니다.
     */
    public Optional<WaitingSetting> findActiveSetting(Store store) {
        return waitingSettingRepository.findActive(store)
                .filter(it -> !it.isDeleted());
    }

    @Transactional
    @KafkaEventHandler(StoreWaitingServiceCreatedEvent.class)
    public void handleStoreWaitingRegisteredEvent(StoreWaitingServiceCreatedEvent event) {
        try {
            StoreService storeService = storeServiceService.findById(event.id());
            waitingSettingRepository.save(WaitingSetting.generateDefaultSetting(storeService));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateAlreadyActive(StoreService storeService) {
        if (waitingSettingRepository.existsByStoreServiceAndIsActiveTrueAndDeletedFalse(storeService)) {
            throw new ApiException(ErrorCode.ALREADY_EXIST_ACTIVE_WAITING_SETTING);
        }
    }
}
