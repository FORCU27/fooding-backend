package im.fooding.app.dto.response.admin.waiting;

import im.fooding.core.model.waiting.WaitingLog;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminWaitingLogResponse(

        @Schema(description = "웨이팅 로그 id", example = "1")
        Long id,

        @Schema(description = "가게 웨이팅 id", example = "1")
        Long storeWaitingId,

        @Schema(description = "웨이팅 로그 타입(WAITING_REGISTRATION, ENTRY)", example = "WAITING_REGISTRATION")
        String type
) {

    public static AdminWaitingLogResponse from(WaitingLog waitingLog) {
        return new AdminWaitingLogResponse(
                waitingLog.getId(),
                waitingLog.getStoreWaiting().getId(),
                waitingLog.getTypeValue()
        );
    }
}
