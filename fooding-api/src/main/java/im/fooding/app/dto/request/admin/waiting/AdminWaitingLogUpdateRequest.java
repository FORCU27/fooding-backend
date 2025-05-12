package im.fooding.app.dto.request.admin.waiting;

import im.fooding.core.dto.request.waiting.WaitingLogUpdateRequest;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLogType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminWaitingLogUpdateRequest(

        @Schema(description = "가게 웨이팅 id", example = "1")
        @NotNull
        Long storeWaitingId,

        @Schema(description = "웨이팅 로그 타입(WAITING_REGISTRATION, ENTRY)", example = "WAITING_REGISTRATION")
        @NotBlank
        String type
) {
        public WaitingLogUpdateRequest toWaitingLogUpdateRequest(Long id, StoreWaiting storeWaiting) {
                return new WaitingLogUpdateRequest(
                        id,
                        storeWaiting,
                        WaitingLogType.of(type)
                );
        }
}
