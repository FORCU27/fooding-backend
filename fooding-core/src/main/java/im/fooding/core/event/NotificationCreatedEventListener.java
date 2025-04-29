package im.fooding.core.event;

import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.model.notification.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationCreatedEventListener {
    private final SlackClient slackClient;

    public NotificationCreatedEventListener(SlackClient slackClient) {
      this.slackClient = slackClient;
    }

    @EventListener
    public void handleNotificationCreatedEvent(NotificationCreatedEvent event) {

      if (event.getChannel() == NotificationChannel.MESSAGE) {
        String slackMessage = String.format(
                "📢 알림 메시지 \n- 제목: %s\n- 내용: %s\n- 수신자: %s",
                event.getTitle(),
                event.getContent(),
                String.join(", ", event.getDestinations())
        );
        slackClient.sendNotificationMessage(slackMessage);
        log.info("알림 슬랙 메시지 전송: 제목={}, 내용={}, 수신자={}",
                event.getTitle(), event.getContent(), String.join(", ", event.getDestinations()));
      }
    }
}
