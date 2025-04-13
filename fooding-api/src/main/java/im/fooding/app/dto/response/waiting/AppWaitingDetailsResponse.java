package im.fooding.app.dto.response.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record AppWaitingDetailsResponse(
        @Schema(description = "웨이팅 정보")
        StoreWaitingResponse waiting,

        @Schema
        List<WaitingLogResponse> waitingLogs
) {
    public static AppWaitingDetailsResponse of(StoreWaiting waiting, List<WaitingLog> waitingLogs) {
        StoreWaitingResponse storeWaitingResponse = StoreWaitingResponse.from(waiting);
        List<WaitingLogResponse> waitingLogResponses = waitingLogs.stream()
                .map(WaitingLogResponse::from)
                .toList();

        return new AppWaitingDetailsResponse(storeWaitingResponse, waitingLogResponses);
    }
}
