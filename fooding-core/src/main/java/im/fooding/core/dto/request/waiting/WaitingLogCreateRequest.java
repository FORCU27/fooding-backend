package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingLogType;

public record WaitingLogCreateRequest(
        StoreWaiting storeWaiting,
        WaitingLogType type
) {

    public WaitingLog toWaitingLog() {
        return new WaitingLog(storeWaiting, type);
    }
}
