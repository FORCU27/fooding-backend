package im.fooding.app.service.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationRequest;
import im.fooding.app.dto.response.admin.notification.AdminNotificationResponse;
import im.fooding.core.event.NotificationCreatedEvent;
import im.fooding.core.model.notification.Notification;
import im.fooding.core.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import im.fooding.core.model.notification.NotificationSortType;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AdminNotificationService {

  private final NotificationService notificationService;
  private final ApplicationEventPublisher publisher;

  public PageResponse<AdminNotificationResponse> list(Pageable pageable, NotificationSortType sortType,
      SortDirection sortDirection) {
    Page<Notification> page = notificationService.findAll(pageable, sortType, sortDirection);
    return PageResponse.of(
        page.getContent().stream().map(AdminNotificationResponse::of).toList(),
        PageInfo.of(page));
  }

  public AdminNotificationResponse retrieve(Long id) {
    Notification notification = notificationService.findById(id);
    return AdminNotificationResponse.of(notification);
  }

  @Transactional
  public Long create(AdminCreateNotificationRequest request) {
    String destinationString = String.join(",", request.getDestinations());

    Notification notification = Notification.builder()
        .source(request.getSource())
        .destination(destinationString)
        .title(request.getTitle())
        .content(request.getContent())
        .channel(request.getChannel())
        .category(request.getCategory())
        .build();

    if (request.getScheduledAt() != null) {
      notification.schedule(request.getScheduledAt());
    } else {
      notification.send();
    }

    Notification savedNotification = notificationService.create(notification);

    publisher.publishEvent(
        new NotificationCreatedEvent(
            savedNotification.getTitle(),
            savedNotification.getContent(),
            request.getDestinations(),
            savedNotification.getChannel(),
            savedNotification.getCategory(),
            // TODO: 서비스 어떻게 관리하는 것이 좋을까?
            "user"
        )
    );

    return savedNotification.getId();
  }

  @Transactional
  public void update(Long id, AdminUpdateNotificationRequest request) {
    notificationService.update(id, request.getTitle(), request.getContent(), request.getChannel(),
        request.getScheduledAt());
  }

  @Transactional
  public void delete(Long id, Long deletedBy) {
    notificationService.delete(id, deletedBy);
  }
}
