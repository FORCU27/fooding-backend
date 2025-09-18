package im.fooding.app.dto.response.admin.waiting;

import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminWaitingSettingResponse(

        @Schema(description = "ID", example = "1")
        Long id,

        @Schema(description = "StoreService ID")
        Long storeServiceId,

        @Schema(description = "라벨", example = "월요일 세팅")
        String label,

        @Schema(description = "최소 입장 인원", example = "2")
        Integer minimumCapacity,

        @Schema(description = "최대 입장 인원", example = "10")
        Integer maximumCapacity,

        @Schema(description = "예상 웨이팅 시간(분)", example = "10")
        Integer estimatedWaitingTimeMinutes,

        @Schema(description = "활성화 상태", example = "true")
        Boolean isActive,

        @Schema(description = "입장 시간 제한", example = "5")
        Integer entryTimeLimitMinutes,

        @Schema(description = "웨이팅 상태", example = "WAITING_CLOSE")
        WaitingStatus status
) {

    public static AdminWaitingSettingResponse from(WaitingSetting waitingSetting) {
        return new AdminWaitingSettingResponse(
                waitingSetting.getId(),
                waitingSetting.getStoreService().getId(),
                waitingSetting.getLabel(),
                waitingSetting.getMinimumCapacity(),
                waitingSetting.getMaximumCapacity(),
                waitingSetting.getEstimatedWaitingTimeMinutes(),
                waitingSetting.isActive(),
                waitingSetting.getEntryTimeLimitMinutes(),
                waitingSetting.getStatus()
        );
    }
}
