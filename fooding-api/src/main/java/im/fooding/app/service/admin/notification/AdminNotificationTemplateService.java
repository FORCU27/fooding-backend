package im.fooding.app.service.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationTemplateRequest;
import im.fooding.app.dto.request.admin.notification.AdminNotificationTemplatePageRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationTemplateRequest;
import im.fooding.app.dto.response.admin.notification.AdminNotificationTemplateResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.service.notification.NotificationTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNotificationTemplateService {

    private final NotificationTemplateService notificationTemplateService;

    public PageResponse<AdminNotificationTemplateResponse> list(AdminNotificationTemplatePageRequest request) {
        Page<AdminNotificationTemplateResponse> notifications = notificationTemplateService.findAll(request.getPageable())
                .map(AdminNotificationTemplateResponse::from);
        return PageResponse.of(notifications.toList(), PageInfo.of(notifications));
    }

    public AdminNotificationTemplateResponse retrieve(String id) {
        return AdminNotificationTemplateResponse.from(notificationTemplateService.getById(new ObjectId(id)));
    }

    @Transactional
    public String create(@Valid AdminCreateNotificationTemplateRequest request) {
        return notificationTemplateService.createNotificationTemplate(
                request.getSubject(),
                request.getContent(),
                request.getType()
        ).toString();
    }

    @Transactional
    public void update(String id, @Valid AdminUpdateNotificationTemplateRequest request) {
        notificationTemplateService.update(
                new ObjectId(id),
                request.getType(),
                request.getSubject(),
                request.getContent()
        );
    }

    @Transactional
    public void delete(String id, Long userId) {
        notificationTemplateService.delete(new ObjectId(id), userId);
    }
}
