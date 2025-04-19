package im.fooding.core.repository.notification;

import im.fooding.core.model.notification.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findAllByUserIdOrderBySentAtDesc(Long userId);

    Optional<UserNotification> findByIdAndUserId(Long id, Long userId);
}
