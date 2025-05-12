package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.WaitingLogCreateRequest;
import im.fooding.core.dto.request.waiting.WaitingLogUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingLogType;
import im.fooding.core.repository.waiting.WaitingLogRepository;
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
public class WaitingLogService {

    private final WaitingLogRepository waitingLogRepository;

    @Transactional
    public void create(WaitingLogCreateRequest request) {
        WaitingLog waitingLog = request.toWaitingLog();
        waitingLogRepository.save(waitingLog);
    }

    public WaitingLog get(long id) {
        return waitingLogRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_LOG_NOT_FOUND));
    }

    public Page<WaitingLog> list(Pageable pageable) {
        return waitingLogRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional
    public void update(WaitingLogUpdateRequest request) {
        WaitingLog waitingLog = get(request.id());
        waitingLog.update(
                request.storeWaiting(),
                request.type()
        );
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        WaitingLog waitingLog = get(id);
        waitingLog.delete(deletedBy);
    }

    @Transactional
    public WaitingLog logRegister(StoreWaiting storeWaiting) {
        return waitingLogRepository.save(new WaitingLog(storeWaiting, WaitingLogType.WAITING_REGISTRATION));
    }

    public Page<WaitingLog> list(long storeWaitingId, Pageable pageable) {
        return waitingLogRepository.findAllByStoreWaitingIdAndDeletedFalse(storeWaitingId, pageable);
    }
}
