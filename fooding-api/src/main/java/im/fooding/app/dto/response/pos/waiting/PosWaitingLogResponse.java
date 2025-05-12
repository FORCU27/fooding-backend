package im.fooding.app.dto.response.pos.waiting;

import im.fooding.core.model.waiting.WaitingLog;
import io.swagger.v3.oas.annotations.media.Schema;

public record PosWaitingLogResponse(
        @Schema(description = "id", example = "1")
        long id,

        @Schema(description = "변동 사항", example = "WAITING_REGISTRATION")
        String type
) {
    public static PosWaitingLogResponse from(WaitingLog waitingLog) {
        return new PosWaitingLogResponse(
                waitingLog.getId(),
                waitingLog.getTypeValue()
        );
    }
}
