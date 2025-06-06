package im.fooding.app.dto.request.admin.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminWaitingUpdateRequest(

        @Schema(description = "가게 아이디", example = "1")
        @NotNull
        long storeId,

        @Schema(description = "웨이팅 상태 (WAITING_OPEN, IMMEDIATE_ENTRY, PAUSED, WAITING_CLOSE)", example = "WAITING_OPEN")
        @NotBlank
        String status
) {
}
