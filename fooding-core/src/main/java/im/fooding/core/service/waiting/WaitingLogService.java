package im.fooding.core.service.waiting;

import im.fooding.core.dto.request.waiting.WaitingLogFilter;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.repository.waiting.WaitingLogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class WaitingLogService {

    private final WaitingLogRepository waitingLogRepository;

    public List<WaitingLog> list(WaitingLogFilter filter) {
        return waitingLogRepository.findAllWithFilter(filter);
    }
}
