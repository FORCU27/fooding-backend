package im.fooding.core.event.auth;

import im.fooding.core.model.notification.NotificationChannel;

public record AuthGetResetUrlByEmailEvent(
        String name,
        String email,
        String resetUrl,
        String senderEmail,
        NotificationChannel channel
) {
}
