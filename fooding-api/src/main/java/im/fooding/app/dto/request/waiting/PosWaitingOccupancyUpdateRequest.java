package im.fooding.app.dto.request.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PosWaitingOccupancyUpdateRequest(
        @NotNull
        @Schema(description = "성인 인원 수", example = "2")
        Integer adultCount,

        @NotNull
        @Schema(description = "유아 인원 수", example = "1")
        Integer infantCount,

        @NotNull
        @Schema(description = "유아용 의자 수", example = "1")
        Integer infantChairCount
) {
}
