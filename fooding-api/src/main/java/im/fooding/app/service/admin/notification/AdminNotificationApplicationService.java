package im.fooding.app.service.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationRequest;
import im.fooding.app.dto.response.admin.manager.AdminManagerResponse;
import im.fooding.app.dto.response.admin.notification.AdminNotificationResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.Notification;
import im.fooding.core.model.notification.NotificationStatus;
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

    // 알림 전체 조회
    public List<AdminNotificationResponse> getAllNotifications() {
      return notificationService.findAll().stream()
              .map(AdminNotificationResponse::of)
              .toList();
    }

    // 알림 상세 조회
    public AdminNotificationResponse getNotification(Long id) {
    Notification notification = notificationService.findById(id);
    return AdminNotificationResponse.of(notification);
    }

    // 알림 생성
    @Transactional
    public Long createNotification(AdminCreateNotificationRequest request) {
      String destinationString = String.join(",", request.getDestinations());

      Notification notification = Notification.builder()
              .source((request.getSource()))
              .destination(destinationString)
              .title(request.getTitle())
              .content(request.getContent())
              .channel(request.getChannel())
              .build();

      if (request.getScheduledAt() != null) {
        notification.schedule(request.getScheduledAt()); // 예약 발송
      } else {
        notification.send(); // 즉시 발송
      }

      return notificationService.create(notification).getId();
    }

    // 알림 수정
    @Transactional
    public void updateNotification(Long id, AdminUpdateNotificationRequest request) {
      notificationService.update(id, request.getTitle(), request.getContent(), request.getChannel(), request.getScheduledAt());
    }

    // 알림 삭제
    @Transactional
    public void deleteNotification(Long id) {
      notificationService.delete(id);
    }
}
