package im.fooding.app.dto.response.admin.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminStoreWaitingResponse(
        @Schema(description = "웨이팅 id", example = "1")
        Long id,

        @Schema(description = "웨이팅 유저 id", example = "1")
        Long userId,

        @Schema(description = "가게 id", example = "1")
        Long storeId,

        @Schema(description = "웨이팅 상태(WAITING, SEATED, CANCELLED)", example = "WAITING")
        String status,

        @Schema(description = "등록 방법(IN_PERSON, ONLINE)", example = "IN_PERSON")
        String channel,

        @Schema(description = "유아용 의자 개수", example = "1")
        Integer infantChairCount,

        @Schema(description = "유아 수", example = "1")
        Integer infantCount,

        @Schema(description = "성인 수", example = "1")
        Integer adultCount,

        @Schema(description = "메모", example = "메모 내용입니다.")
        String memo,

        @Schema(description = "생성일시", example = "2025-07-30T06:01:16.711Z")
        String createdAt,

        @Schema(description = "수정일시", example = "2025-07-30T06:01:16.711Z")
        String updatedAt
) {

    public static AdminStoreWaitingResponse from(StoreWaiting waiting) {
            Long userId = null;
            if (waiting.getWaitingUser() != null) {
                    userId = waiting.getWaitingUser().getId();
            }
            return new AdminStoreWaitingResponse(
                    waiting.getId(),
                    userId,
                    waiting.getStoreId(),
                    waiting.getStatus().name(),
                    waiting.getChannel().name(),
                    waiting.getInfantChairCount(),
                    waiting.getInfantCount(),
                    waiting.getAdultCount(),
                    waiting.getMemo(),
                    waiting.getCreatedAt().toString(),
                    waiting.getUpdatedAt().toString()
            );
    }
}
