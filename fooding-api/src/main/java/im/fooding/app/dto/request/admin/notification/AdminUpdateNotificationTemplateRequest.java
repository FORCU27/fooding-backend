package im.fooding.app.dto.request.admin.notification;

import im.fooding.core.model.notification.NotificationTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AdminUpdateNotificationTemplateRequest {

    @Schema(description = "종류", example = "WaitingCreatedEmail")
    @NotNull
    NotificationTemplate.Type type;

    @Schema(description = "제목", example = "웨이팅 등록 완료")
    @NotBlank
    String subject;

    @Schema(description = "본문", example = "[%s]에 웨이팅이 등록되었습니다.")
    @NotBlank
    String content;
}
