package im.fooding.core.service.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.repository.waiting.WaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingService {

    private final WaitingRepository waitingRepository;

    public Waiting getById(long id) {
        return waitingRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_NOT_FOUND));
    }

    public void validate(Waiting waiting) {
        if (waiting.getStatus() != WaitingStatus.WAITING_OPEN) {
            throw new ApiException(ErrorCode.WAITING_NOT_OPENED);
        }
    }
}
