package im.fooding.app.dto.response.user.notification;

import im.fooding.core.model.notification.UserNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserNotificationResponse {
    @Schema(description = "유저 알림 ID", example = "1")
    private long id;

    @Schema(description = "알림 제목", example = "서비스 점검 안내")
    private String title;

    @Schema(description = "알림 내용", example = "4월 20일 12시에 시스템 점검이 있습니다.")
    private String content;

    @Schema(description = "발송 시간", example = "2025-04-17 12:00:00")
    private LocalDateTime sentAt;

    @Schema(description = "읽음 여부", example = "false")
    private boolean isRead;

    @Schema(description = "알림 카테고리", example = "SERVICE")
    private String category;

    public UserNotificationResponse(long id, String title, String content, LocalDateTime sentAt, boolean isRead, String category) {
      this.id = id;
      this.title = title;
      this.content = content;
      this.sentAt = sentAt;
      this.isRead = isRead;
      this.category = category;
    }

    public static UserNotificationResponse from(UserNotification userNotification) {
      return new UserNotificationResponse(
              userNotification.getId(),
              userNotification.getTitle(),
              userNotification.getContent(),
              userNotification.getSentAt(),
              userNotification.isRead(),
              userNotification.getCategory()
      );
    }
}


