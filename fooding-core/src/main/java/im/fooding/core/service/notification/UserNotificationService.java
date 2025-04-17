package im.fooding.core.service.notification;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.UserNotification;
import im.fooding.core.repository.notification.UserNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserNotification getNotification(Long userId, Long notificationId) {
      return userNotificationRepository.findByIdAndUserId(userId, notificationId)
              .orElseThrow(() -> new ApiException(ErrorCode.USER_NOTIFICATION_NOT_FOUND));
    }
}
