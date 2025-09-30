package im.fooding.core.event.auth;

import im.fooding.core.model.notification.NotificationChannel;

public record AuthPhoneAuthenticateEvent(
        String name,
        int code,
        String senderNumber,
        String phoneNumber,
        NotificationChannel channel
) {
}
