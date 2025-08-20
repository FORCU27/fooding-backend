package im.fooding.core.service.notification;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.NotificationTemplate;
import im.fooding.core.repository.notification.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTemplateService {

    private final NotificationTemplateRepository notificationTemplateRepository;

    public ObjectId createNotificationTemplate(
            String subject,
            String content,
            NotificationTemplate.Type type
    ) {
        NotificationTemplate newNotificationTemplate = new NotificationTemplate(type, subject, content);

        notificationTemplateRepository.save(newNotificationTemplate);

        return newNotificationTemplate.getId();
    }

    public NotificationTemplate getNotificationTemplateByType(NotificationTemplate.Type type) {
        return notificationTemplateRepository.findByType(type)
                .filter(it ->!it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.NOTIFICATION_TEMPLATE_NOT_FOUND));
    }
}
