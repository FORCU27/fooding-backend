package im.fooding.app.dto.request.waiting;

import io.swagger.v3.oas.annotations.media.Schema;

public record PosWaitingMemoUpdateRequest(
        @Schema(description = "메모", example = "비건, 조용한 자리, 단골")
        String memo
) {
}
