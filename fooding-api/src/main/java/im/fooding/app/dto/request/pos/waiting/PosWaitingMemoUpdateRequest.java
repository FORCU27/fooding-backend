package im.fooding.app.dto.request.pos.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PosWaitingMemoUpdateRequest(
        @Schema(description = "메모", example = "비건, 조용한 자리, 단골")
        @NotNull
        String memo
) {
}
