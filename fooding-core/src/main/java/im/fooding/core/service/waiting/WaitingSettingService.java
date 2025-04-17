package im.fooding.core.service.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.repository.waiting.WaitingSettingRepository;
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

    public WaitingSetting getActiveSetting() {
        return waitingSettingRepository.findByIsActiveIsTrue()
                .orElseThrow(() -> new ApiException(ErrorCode.ACTIVE_WAITING_SETTING_NOT_FOUND));
    }
}
