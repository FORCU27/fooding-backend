package im.fooding.core.service.notification;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.Notification;
import im.fooding.core.model.notification.NotificationChannel;
import im.fooding.core.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // 알림 전체 조회
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    // 알림 상세 조회
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOTIFICATION_NOT_FOUND));
    }

    // 알림 생성
    @Transactional
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    // 알림 수정
    @Transactional
    public void update(Long id, String title, String content, NotificationChannel channel, LocalDateTime scheduledAt) {
        Notification notification = findById(id);
        notification.update(title, content, channel, scheduledAt);
    }

    // 알림 삭제
    @Transactional
    public void delete(Long id) {
        Notification notification = findById(id);
        notificationRepository.delete(notification);
    }
}
