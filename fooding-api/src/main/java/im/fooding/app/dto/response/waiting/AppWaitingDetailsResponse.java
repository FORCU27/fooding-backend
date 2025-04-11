package im.fooding.app.dto.response.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record AppWaitingDetailsResponse(
        @Schema(description = "웨이팅 정보")
        WaitingResponse waiting,

        @Schema
        List<WaitingLogResponse> waitingLogs
) {
    public static AppWaitingDetailsResponse of(StoreWaiting waiting, List<WaitingLog> waitingLogs) {
        WaitingResponse waitingResponse = WaitingResponse.from(waiting);
        List<WaitingLogResponse> waitingLogResponses = waitingLogs.stream()
                .map(WaitingLogResponse::from)
                .toList();

        return new AppWaitingDetailsResponse(waitingResponse, waitingLogResponses);
    }
}
