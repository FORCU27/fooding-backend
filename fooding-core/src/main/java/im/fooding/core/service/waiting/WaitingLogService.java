package im.fooding.core.service.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingLogType;
import im.fooding.core.repository.waiting.WaitingLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WaitingLogService {

    private final WaitingLogRepository waitingLogRepository;

    public WaitingLog logRegister(StoreWaiting storeWaiting) {
        return waitingLogRepository.save(new WaitingLog(storeWaiting));
    }
}
