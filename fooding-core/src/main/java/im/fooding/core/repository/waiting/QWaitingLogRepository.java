package im.fooding.core.repository.waiting;

import im.fooding.core.dto.request.waiting.WaitingLogFilter;
import im.fooding.core.model.waiting.WaitingLog;
import java.util.List;

public interface QWaitingLogRepository {

    List<WaitingLog> findAllWithFilter(WaitingLogFilter filter);
}
