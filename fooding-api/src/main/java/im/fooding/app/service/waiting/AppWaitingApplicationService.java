package im.fooding.app.service.waiting;

import im.fooding.app.dto.response.waiting.AppWaitingDetailsResponse;
import im.fooding.core.dto.request.waiting.WaitingLogFilter;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.service.waiting.StoreWaitingService;
import im.fooding.core.service.waiting.WaitingLogService;
import java.util.List;
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
    private final WaitingLogService waitingLogService;

    public AppWaitingDetailsResponse details(long id) {
        StoreWaiting storeWaiting = storeWaitingService.getStoreById(id);

        WaitingLogFilter waitingLogFilter = new WaitingLogFilter(storeWaiting.getId(), null);
        List<WaitingLog> waitingLogs = waitingLogService.list(waitingLogFilter);

        return AppWaitingDetailsResponse.of(storeWaiting, waitingLogs);
    }
}
