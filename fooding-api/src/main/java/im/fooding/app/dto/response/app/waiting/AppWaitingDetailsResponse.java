package im.fooding.app.dto.response.app.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record AppWaitingDetailsResponse(
        @Schema(description = "웨이팅 정보")
        AppStoreWaitingResponse waiting,

        @Schema
        List<AppWaitingLogResponse> waitingLogs
) {
    public static AppWaitingDetailsResponse of(StoreWaiting waiting, List<WaitingLog> waitingLogs) {
        AppStoreWaitingResponse storeWaitingResponse = AppStoreWaitingResponse.from(waiting);
        List<AppWaitingLogResponse> waitingLogResponses = waitingLogs.stream()
                .map(AppWaitingLogResponse::from)
                .toList();

        return new AppWaitingDetailsResponse(storeWaitingResponse, waitingLogResponses);
    }
}
