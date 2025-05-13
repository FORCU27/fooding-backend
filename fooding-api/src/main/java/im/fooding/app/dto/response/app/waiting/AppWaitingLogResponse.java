package im.fooding.app.dto.response.app.waiting;

import im.fooding.core.model.waiting.WaitingLog;
import io.swagger.v3.oas.annotations.media.Schema;

public record AppWaitingLogResponse(
        @Schema(description = "id", example = "1")
        long id,

        @Schema(description = "변동 사항", example = "WAITING_REGISTRATION")
        String type
) {
    public static AppWaitingLogResponse from(WaitingLog waitingLog) {
        return new AppWaitingLogResponse(
                waitingLog.getId(),
                waitingLog.getTypeValue()
        );
    }
}
