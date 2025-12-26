package im.fooding.realtime.app.service.waiting;

import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.realtime.app.domain.waiting.StoreWaiting;
import im.fooding.realtime.app.repository.waiting.StoreWaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StoreWaitingService {

    private final StoreWaitingRepository storeWaitingRepository;

    public Mono<Page<StoreWaiting>> listByStoreId(long storeId, StoreWaitingStatus status, Pageable pageable) {
        return storeWaitingRepository.listByStoreId(storeId, status, pageable);
    }
}
