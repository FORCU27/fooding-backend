package im.fooding.app.service.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationRequest;
import im.fooding.app.dto.response.admin.notification.AdminNotificationResponse;
import im.fooding.core.model.notification.Notification;
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

      return notificationService.create(notification).getId();
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
