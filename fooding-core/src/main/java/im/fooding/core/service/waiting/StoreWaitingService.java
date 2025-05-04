package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.StoreWaitingCreateRequest;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.dto.request.waiting.StoreWaitingUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoreWaitingService {

    private final StoreWaitingRepository storeWaitingRepository;

    @Transactional
    public StoreWaiting create(StoreWaitingCreateRequest request) {
        StoreWaiting storeWaiting = request.toStoreWaiting(generateCallNumber());
        return storeWaitingRepository.save(storeWaiting);
    }

    public Page<StoreWaiting> list(Pageable pageable) {
        return storeWaitingRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional
    public StoreWaiting update(StoreWaitingUpdateRequest request) {
        StoreWaiting storeWaiting = get(request.id());
        storeWaiting.update(
                request.user(),
                request.store(),
                StoreWaitingStatus.of(request.status()),
                StoreWaitingChannel.of(request.channel()),
                request.infantChairCount(),
                request.infantCount(),
                request.adultCount(),
                request.memo()
        );

        return storeWaitingRepository.save(storeWaiting);
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        StoreWaiting storeWaiting = get(id);

        storeWaiting.delete(deletedBy);
    }

    public Page<StoreWaiting> list(StoreWaitingFilter filter, Pageable pageable) {
        return storeWaitingRepository.findAllWithFilterAndDeletedFalse(filter, pageable);
    }

    public StoreWaiting get(long id) {
        return storeWaitingRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_WAITING_NOT_FOUND));
    }

    @Transactional
    public StoreWaiting call(long id) {
        StoreWaiting storeWaiting = get(id);
        if (storeWaiting.getStatus() != StoreWaitingStatus.WAITING) {
            throw new ApiException(ErrorCode.STORE_WAITING_ILLEGAL_STATE_CALL);
        }
        storeWaiting.call();

        return storeWaiting;
    }

    @Transactional
    public StoreWaiting register(StoreWaitingRegisterRequest request) {
        int callNumber = generateCallNumber();

        StoreWaiting storeWaiting = StoreWaiting.builder()
                .user(request.user())
                .store(request.store())
                .status(StoreWaitingStatus.WAITING)
                .callNumber(callNumber)
                .channel(StoreWaitingChannel.of(request.channel()))
                .infantChairCount(request.infantChairCount())
                .infantCount(request.infantCount())
                .adultCount(request.adultCount())
                .memo("")
                .build();

        return storeWaitingRepository.save(storeWaiting);
    }

    public void validate(Waiting waiting) {
        if (waiting.getStatus() != WaitingStatus.WAITING_OPEN) {
            throw new ApiException(ErrorCode.WAITING_NOT_OPENED);
        }
    }

    @Transactional
    public void seat(long id) {
        StoreWaiting storeWaiting = get(id);

        storeWaiting.seat();
    }

    public int getOrder(long id) {
        StoreWaiting storeWaiting = storeWaitingRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_WAITING_NOT_FOUND));

        return (int) storeWaitingRepository.countByStatusAndCreatedAtBeforeAndDeletedFalse(
                storeWaiting.getStatus(),
                storeWaiting.getCreatedAt()
        ) + 1;
    }

    @Transactional
    public StoreWaiting cancel(long id) {
        StoreWaiting storeWaiting = get(id);

        storeWaiting.cancel();
        return storeWaitingRepository.save(storeWaiting);
    }

    public void revert(long id) {
        StoreWaiting storeWaiting = get(id);

        storeWaiting.revert();
    }

    public boolean exists(Store store, StoreWaitingStatus status) {
        return storeWaitingRepository.existsByStoreAndStatusAndDeletedFalse(store, status);
    }

    // TODO: 추후에 redis 로 개선
    private int generateCallNumber() {
        return (int) storeWaitingRepository.countCreatedOnAndDeletedFalse(LocalDate.now()) + 1;
    }
}
