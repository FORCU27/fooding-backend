package im.fooding.app.dto.request.app.waiting;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
public class AppWaitingReservationRequest extends BasicSearch {

    @Schema(description = "방문 상태 (SCHEDULED, COMPLETED, NOT_VISITED)", requiredMode = RequiredMode.REQUIRED, example = "SCHEDULED")
    VisitStatus visitStatus;

    @RequiredArgsConstructor
    public enum VisitStatus {
        SCHEDULED,      // 방문예정
        COMPLETED,      // 방문완료
        NOT_VISITED,    // 취소/노쇼
        ;
    }
}
