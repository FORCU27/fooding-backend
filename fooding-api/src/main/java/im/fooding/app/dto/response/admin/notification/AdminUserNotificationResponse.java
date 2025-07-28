package im.fooding.app.dto.response.admin.notification;

import im.fooding.core.model.notification.UserNotification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminUserNotificationResponse {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String category;
    private java.time.LocalDateTime sentAt;
    private boolean isRead;

    @Builder
    private AdminUserNotificationResponse(Long id, Long userId, String title, String content, String category,
            java.time.LocalDateTime sentAt, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }

    public static AdminUserNotificationResponse from(UserNotification notification) {
        return AdminUserNotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUser().getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .sentAt(notification.getSentAt())
                .category(notification.getCategory())
                .isRead(notification.isRead())
                .build();
    }
}