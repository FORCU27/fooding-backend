package im.fooding.core.dto.request.waiting;

import im.fooding.core.model.waiting.WaitingLogType;

public record WaitingLogFilter(
    Long storeWaitingId,
    WaitingLogType type
) {
}
