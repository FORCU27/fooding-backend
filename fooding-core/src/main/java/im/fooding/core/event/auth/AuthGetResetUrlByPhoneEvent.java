package im.fooding.core.event.auth;

import im.fooding.core.model.notification.NotificationChannel;

public record AuthGetResetUrlByPhoneEvent(
        String name,
        String phoneNumber,
        String resetUrl,
        String senderNumber,
        NotificationChannel channel
) {
}
