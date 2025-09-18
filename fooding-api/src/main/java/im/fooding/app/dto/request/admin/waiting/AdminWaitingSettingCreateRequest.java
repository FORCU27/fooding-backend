package im.fooding.app.dto.request.admin.waiting;

import im.fooding.core.dto.request.waiting.WaitingSettingCreateRequest;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.waiting.WaitingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminWaitingSettingCreateRequest(

        @Schema(description = "웨이팅 아이디")
        @NotNull
        Long storeServiceId,

        @Schema(description = "라벨", example = "월요일 세팅")
        @NotBlank
        String label,

        @Schema(description = "최소 입장 인원", example = "2")
        @Min(0)
        Integer minimumCapacity,

        @Schema(description = "최대 입장 인원", example = "10")
        @NotNull
        Integer maximumCapacity,

        @Schema(description = "예상 웨이팅 시간(분)", example = "10")
        @Min(0)
        Integer estimatedWaitingTimeMinutes,

        @Schema(description = "활성화 상태", example = "true")
        @NotNull
        Boolean isActive,

        @Schema(description = "입장 시간 제한", example = "5")
        @Min(0)
        Integer entryTimeLimitMinutes,

        @Schema(description = "웨이팅 상태", example = "WAITING_CLOSE")
        WaitingStatus status
) {

        public WaitingSettingCreateRequest toWaitingSettingCreateRequest(StoreService storeService) {
                return  WaitingSettingCreateRequest.builder()
                        .storeService(storeService)
                        .label(label)
                        .minimumCapacity(minimumCapacity)
                        .maximumCapacity(maximumCapacity)
                        .estimatedWaitingTimeMinutes(estimatedWaitingTimeMinutes)
                        .isActive(isActive)
                        .entryTimeLimitMinutes(entryTimeLimitMinutes)
                        .status(status)
                        .build();
        }
}
