package im.fooding.app.dto.response.admin.notification;

import im.fooding.core.model.notification.NotificationTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminNotificationTemplateResponse {

    @Schema(description = "ID", example = "6889bf12db34d469470f868e")
    String id;

    @Schema(description = "종류", example = "WaitingCreatedEmail")
    NotificationTemplate.Type type;

    @Schema(description = "제목", example = "웨이팅 등록 완료")
    String subject;

    @Schema(description = "본문", example = "[%s]에 웨이팅이 등록되었습니다.")
    String content;

    public static AdminNotificationTemplateResponse from(NotificationTemplate notificationTemplate) {
        return AdminNotificationTemplateResponse.builder()
                .id(notificationTemplate.getId().toString())
                .type(notificationTemplate.getType())
                .subject(notificationTemplate.getSubject())
                .content(notificationTemplate.getContent())
                .build();
    }
}
