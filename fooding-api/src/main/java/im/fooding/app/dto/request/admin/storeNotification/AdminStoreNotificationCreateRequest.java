package im.fooding.app.dto.request.admin.storeNotification;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AdminStoreNotificationCreateRequest {

    @NotNull
    @Schema(description = "가게 ID", example = "1")
    Long storeId;

    @NotBlank
    @Schema(description = "제목", example = "홍길동님이 예약하셨습니다")
    String title;

    @NotBlank
    @Schema(description = "내용", example = "홍길동님이 예약하셨습니다")
    String content;

    @NotNull
    @Schema(description = "종류", example = "RESERVATION")
    String category;

    @NotNull
    @Schema(description = "관련 URL", example = "https://stage.fooding.im")
    String linkUrl;
}
