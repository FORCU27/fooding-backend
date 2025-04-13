package im.fooding.app.dto.response.admin.notification;

import im.fooding.core.model.notification.Notification;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.model.notification.NotificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdminNotificationResponse {
    @Schema(description = "id", example = "1")
    private long id;

    @Schema(description = "알림 발신자", example = "system@example.com")
    private String source;

    @Schema(description = "알림 수신자", example = "user@example.com")
    private List<String> destinations;

    @Schema(description = "알림 제목", example = "긴급 시스템 점검 안내")
    private String title;

    @Schema(description = "알림 내용", example = "오후 1시 시스템 점검 예정입니다.")
    private String content;

    @Schema(description = "알림 채널", example = "MAIL")
    private NotificationChannel channel;

    @Schema(description = "알림 상태", example = "PENDING")
    private NotificationStatus status;

    @Schema(description = "보낸 시간", example = "2025-04-11 13:00:00")
    private LocalDateTime sentAt;

    @Schema(description = "읽은 시간", example = "2025-04-11 14:00:00")
    private LocalDateTime readAt;

    @Schema(description = "예약 발송 시간", example = "2025-04-11 13:00:00")
    private LocalDateTime scheduledAt;

    @Builder
    private AdminNotificationResponse(long id, String source, List<String> destinations, String title, String content, NotificationChannel channel, NotificationStatus status, LocalDateTime sentAt, LocalDateTime readAt, LocalDateTime scheduledAt ) {
      this.id = id;
      this.source = source;
      this.destinations = destinations;
      this.title = title;
      this.content = content;
      this.channel = channel;
      this.status = status;
      this.sentAt = sentAt;
      this.readAt = readAt;
      this.scheduledAt = scheduledAt;
    }

    public static AdminNotificationResponse of(Notification notification) {
      return AdminNotificationResponse.builder()
              .id(notification.getId())
              .source(notification.getSource())
              .destinations(Arrays.asList(notification.getDestination().split(",")))
              .title(notification.getTitle())
              .content(notification.getContent())
              .channel(notification.getChannel())
              .status(notification.getStatus())
              .sentAt(notification.getSentAt())
              .readAt(notification.getReadAt())
              .scheduledAt(notification.getScheduledAt())
              .build();
    }
}
