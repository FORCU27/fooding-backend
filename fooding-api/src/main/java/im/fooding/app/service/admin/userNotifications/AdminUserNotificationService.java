package im.fooding.app.service.admin.userNotifications;

import im.fooding.app.dto.request.admin.userNotifications.AdminCreateUserNotificationRequest;
import im.fooding.app.dto.response.admin.notification.AdminUserNotificationResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.notification.UserNotification;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import im.fooding.core.service.notification.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserNotificationService {
    private final UserNotificationService userNotificationService;
    private final UserRepository userRepository;

    public PageResponse<AdminUserNotificationResponse> list(Pageable pageable, String sortType,
            SortDirection sortDirection) {
        // 정렬 옵션은 추후 확장 가능. 현재는 sentAt DESC 고정
        Page<UserNotification> page = userNotificationService.getUserNotifications(pageable);
        return PageResponse.of(
                page.getContent().stream().map(AdminUserNotificationResponse::from).toList(),
                PageInfo.of(page));
    }

    public AdminUserNotificationResponse retrieve(Long id) {
        UserNotification notification = userNotificationService.getNotificationById(id);
        return AdminUserNotificationResponse.from(notification);
    }

    @Transactional
    public Long create(AdminCreateUserNotificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserId()));
        UserNotification notification = userNotificationService.create(user, request.getTitle(), request.getContent(), request.getCategory(),
                LocalDateTime.now());
        return notification.getId();
    }

    @Transactional
    public void delete(Long id) {
        UserNotification notification = userNotificationService.getNotificationById(id);
        userNotificationService.delete(notification);
    }
}