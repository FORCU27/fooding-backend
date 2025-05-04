package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingLogType;

public record WaitingLogUpdateRequest(
        Long id,
        StoreWaiting storeWaiting,
        WaitingLogType type
) {
}
