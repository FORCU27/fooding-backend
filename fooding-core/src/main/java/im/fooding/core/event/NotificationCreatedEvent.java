package im.fooding.core.event;

import im.fooding.core.model.notification.Notification;

public class NotificationCreatedEvent {
    private final Notification notification;

    public NotificationCreatedEvent(Notification notification) {
      this.notification = notification;
    }

    public Notification getNotification() {
      return notification;
    }
}
