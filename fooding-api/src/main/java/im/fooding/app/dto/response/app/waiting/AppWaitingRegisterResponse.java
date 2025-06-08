package im.fooding.app.dto.response.app.waiting;

import io.swagger.v3.oas.annotations.media.Schema;

public record AppWaitingRegisterResponse(
        @Schema(description = "호출 번호", example = "1")
        long callNumber,

        @Schema(description = "현재 웨이팅", example = "10")
        long waitingTurn,

        @Schema(description = "예상 시간(분)", example = "100")
        long expectedTimeMinute,

        @Schema(description = "최근 입장(분)", example = "5")
        long recentEntryTimeMinute
) {
}
