package im.fooding.core.service.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
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
public class StoreWaitingService {

    private final StoreWaitingRepository storeWaitingRepository;

    public Page<StoreWaiting> getAllByStoreIdAndStatus(long storeId, String statusValue, Pageable pageable) {
        StoreWaitingStatus status = StoreWaitingStatus.of(statusValue);

        return storeWaitingRepository.findAllByStoreIdAndStatus(storeId, status, pageable);
    }
}
