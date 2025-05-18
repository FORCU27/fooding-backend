package im.fooding.core.service.notification;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.UserNotification;
import im.fooding.core.repository.notification.UserNotificationRepository;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserNotificationService {
  private final UserNotificationRepository userNotificationRepository;

  public List<UserNotification> getUserNotifications(Long userId) {
    return userNotificationRepository.findAllByUserIdOrderBySentAtDesc(userId);
  }

  public Page<UserNotification> getUserNotifications(Long userId, Pageable pageable) {
    return userNotificationRepository.findAllByUserIdOrderBySentAtDesc(userId, pageable);
  }

  public UserNotification getNotification(Long userId, Long notificationId) {
    UserNotification notification = userNotificationRepository.findById(notificationId)
        .filter(it -> !it.isDeleted())
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOTIFICATION_NOT_FOUND));

    if (!notification.getUser().getId().equals(userId)) {
      throw new ApiException(ErrorCode.USER_NOTIFICATION_FORBIDDEN);
    }

    return notification;
  }

  public Page<UserNotification> getUserNotifications(Pageable pageable) {
    return userNotificationRepository.findAll(pageable);
  }

  public UserNotification getNotificationById(Long notificationId) {
    return userNotificationRepository.findById(notificationId)
        .filter(it -> !it.isDeleted())
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOTIFICATION_NOT_FOUND));
  }

  @Transactional
  public UserNotification create(User user, String title, String content, LocalDateTime sentAt) {
    UserNotification notification = UserNotification.builder()
        .user(user)
        .title(title)
        .content(content)
        .sentAt(sentAt)
        .build();
    return userNotificationRepository.save(notification);
  }

  @Transactional
  public void update(UserNotification notification, String title, String content) {
    notification.update(title, content);
  }

  @Transactional
  public void delete(UserNotification notification) {
    notification.delete(0L);
  }
}
