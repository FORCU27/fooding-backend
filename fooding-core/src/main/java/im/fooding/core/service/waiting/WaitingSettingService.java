package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.WaitingSettingCreateRequest;
import im.fooding.core.dto.request.waiting.WaitingSettingUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.repository.waiting.WaitingSettingRepository;
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

    @Transactional
    public void create(WaitingSettingCreateRequest request) {
        if (request.isActive()) {
            validateAlreadyActive(request.waiting());
        }

        WaitingSetting waitingSetting = WaitingSetting.builder()
                .waiting(request.waiting())
                .label(request.label())
                .minimumCapacity(request.minimumCapacity())
                .maximumCapacity(request.maximumCapacity())
                .estimatedWaitingTimeMinutes(request.estimatedWaitingTimeMinutes())
                .isActive(request.isActive())
                .entryTimeLimitMinutes(request.entryTimeLimitMinutes())
                .build();

        waitingSettingRepository.save(waitingSetting);
    }

    public WaitingSetting get(long id) {
        return waitingSettingRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_SETTING_NOT_FOUND));
    }

    public Page<WaitingSetting> list(Pageable pageable) {
        return waitingSettingRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional
    public void update(WaitingSettingUpdateRequest request) {
        WaitingSetting waitingSetting = get(request.id());
        if (request.isActive() && !waitingSetting.isActive()) {
            validateAlreadyActive(request.waiting());
        }

        waitingSetting.update(
                request.waiting(),
                request.label(),
                request.minimumCapacity(),
                request.maximumCapacity(),
                request.estimatedWaitingTimeMinutes(),
                request.isActive(),
                request.entryTimeLimitMinutes()
        );
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

    /**
     * 가게의 대기 시간을 조회합니다.
     *  위에 있는 경우 active가 false일 때 예외가 발생하는 문제로 메서드 Optional로 감싼 메서드를 추가했습니다.
     */
    public Optional<WaitingSetting> findActiveSetting(Store store) {
        return waitingSettingRepository.findActive(store)
                .filter(it -> !it.isDeleted());
    }

    private void validateAlreadyActive(Waiting waiting) {
        if (waitingSettingRepository.existsByWaitingAndIsActiveTrueAndDeletedFalse(waiting)) {
            throw new ApiException(ErrorCode.ALREADY_EXIST_ACTIVE_WAITING_SETTING);
        }
    }
}
