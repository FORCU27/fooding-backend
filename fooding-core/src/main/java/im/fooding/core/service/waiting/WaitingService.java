package im.fooding.core.service.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.repository.waiting.WaitingRepository;
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
public class WaitingService {

    private final WaitingRepository waitingRepository;

    @Transactional
    public Waiting create(Store store, WaitingStatus status) {
        if (waitingRepository.existsByStore(store)) {
            throw new ApiException(ErrorCode.DUPLICATED_STORE_BY_WAITING);
        }
        Waiting waiting = new Waiting(store, status);

        return waitingRepository.save(waiting);
    }

    public Page<Waiting> list(Pageable pageable) {
        return waitingRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional
    public Waiting update(long id, Store store, WaitingStatus status) {
        Waiting waiting = getById(id);

        waiting.update(store, status);

        return waiting;
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        Waiting waiting = getById(id);

        waiting.delete(deletedBy);
    }

    public Waiting getById(long id) {
        return waitingRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_NOT_FOUND));
    }

    @Transactional
    public void updateStatus(long id, WaitingStatus status) {
        Waiting waiting = getById(id);
        waiting.updateStatus(status);
    }

    public boolean isOpen(Store store) {
        Waiting waiting = waitingRepository.findByStore(store)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.WAITING_NOT_FOUND));
        return waiting.getStatus() == WaitingStatus.WAITING_OPEN;
    }
}
