package im.fooding.app.service.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationRequest;
import im.fooding.app.dto.response.admin.notification.AdminNotificationResponse;
import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.model.notification.Notification;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AdminNotificationApplicationService {

    private final NotificationService notificationService;
    private final SlackClient slackClient;

    public List<AdminNotificationResponse> list() {
      return notificationService.findAll().stream()
              .map(AdminNotificationResponse::of)
              .toList();
    }

    public AdminNotificationResponse retrieve(Long id) {
    Notification notification = notificationService.findById(id);
    return AdminNotificationResponse.of(notification);
    }

  @Transactional
  public Long create(AdminCreateNotificationRequest request) {
    String destinationString = String.join(",", request.getDestinations());

    Notification notification = Notification.builder()
            .source((request.getSource()))
            .destination(destinationString)
            .title(request.getTitle())
            .content(request.getContent())
            .channel(request.getChannel())
            .build();

    if (request.getScheduledAt() != null) {
      notification.schedule(request.getScheduledAt());
    } else {
      notification.send();
    }

    Notification savedNotification = notificationService.create(notification);

    if (notification.getChannel() == NotificationChannel.MESSAGE) {
      String slackMessage = String.format(
              "📢 알림 메시지 \n- 제목: %s\n- 내용: %s\n- 수신자: %s",
              notification.getTitle(),
              notification.getContent(),
              notification.getDestination()
      );
      slackClient.sendNotificationMessage(slackMessage);
    }

    return savedNotification.getId();
  }

    @Transactional
    public void update(Long id, AdminUpdateNotificationRequest request) {
      notificationService.update(id, request.getTitle(), request.getContent(), request.getChannel(), request.getScheduledAt());
    }

    @Transactional
    public void delete(Long id, Long deletedBy) {
      notificationService.delete(id, deletedBy);
    }
}
