package im.fooding.core.service.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StoreWaitingService {

    private final StoreWaitingRepository storeWaitingRepository;

    public StoreWaiting getStoreWaiting(long id) {
        return storeWaitingRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_WAITING_NOT_FOUND));
    }
}
