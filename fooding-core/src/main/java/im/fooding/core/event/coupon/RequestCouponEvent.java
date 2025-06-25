package im.fooding.core.event.coupon;

import im.fooding.core.model.notification.NotificationChannel;

public record RequestCouponEvent(
        String name,
        String receiverNumber,
        String senderNumber,
        NotificationChannel channel
) {
}
