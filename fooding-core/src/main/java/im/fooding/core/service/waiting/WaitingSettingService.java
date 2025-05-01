package im.fooding.core.service.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.repository.waiting.WaitingSettingRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingSettingService {

    private final WaitingSettingRepository waitingSettingRepository;

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
}
