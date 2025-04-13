package im.fooding.app.service.waiting;

import im.fooding.app.dto.response.waiting.StoreWaitingResponse;
import im.fooding.core.service.waiting.StoreWaitingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AppWaitingApplicationService {

    private final StoreWaitingService storeWaitingService;

    public StoreWaitingResponse details(long id) {
        return StoreWaitingResponse.from(storeWaitingService.getStoreWaiting(id));
    }
}
