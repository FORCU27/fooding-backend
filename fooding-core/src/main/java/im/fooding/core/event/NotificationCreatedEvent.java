package im.fooding.core.event;

import im.fooding.core.model.notification.NotificationChannel;

import java.util.List;

public class NotificationCreatedEvent {
    private final String title;
    private final String content;
    private final List<String> destinations;
    private final NotificationChannel channel;

    public NotificationCreatedEvent(String title, String content, List<String> destinations, NotificationChannel channel){
      this.title = title;
      this.content = content;
      this.destinations = destinations;
      this.channel = channel;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<String> getDestinations() { return destinations; }
    public NotificationChannel getChannel() { return channel; }
}
