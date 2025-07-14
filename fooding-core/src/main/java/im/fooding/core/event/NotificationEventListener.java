package im.fooding.core.event;

import im.fooding.core.global.infra.fcm.FcmClient;
import im.fooding.core.global.infra.fcm.FcmMetadata;
import im.fooding.core.global.infra.fcm.SendFcmDto;
import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.model.notification.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationEventListener {
    private final SlackClient slackClient;

    private final FcmClient fcmClient;

    public NotificationEventListener(SlackClient slackClient, FcmClient fcmClient) {
      this.slackClient = slackClient;

        this.fcmClient = fcmClient;
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

      if (event.getChannel() == NotificationChannel.PUSH) {
          // TODO: 삭제
          String slackMessage = String.format(
                  "📢 푸시 메시지 \n- 제목: %s\n- 내용: %s\n- 수신자: %s",
                  event.getTitle(),
                  event.getContent(),
                  String.join(", ", event.getDestinations())
          );
          slackClient.sendNotificationMessage(slackMessage);

          // TODO: 유저 개인, 전체 푸시 구분
          fcmClient.sendMessage(event.getService(), SendFcmDto.builder()
                  .title("제목")
                  .content("내용")
                  .metadata(FcmMetadata.builder()
                          .type("broadcast")
                          .imageUrl("https://example.com/image.jpg")
                          .urlPath("https://example.com")
                          .badge("1")
                          .build())
                  .build());
      }
    }
}
