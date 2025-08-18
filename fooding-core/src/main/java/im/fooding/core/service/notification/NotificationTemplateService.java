package im.fooding.core.service.notification;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.NotificationTemplate;
import im.fooding.core.model.notification.NotificationTemplate.Type;
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
            Type type,
            String contact
    ) {
        NotificationTemplate newNotificationTemplate = NotificationTemplate.builder()
                .subject(subject)
                .content(content)
                .type(type)
                .contact(contact)
                .build();

        notificationTemplateRepository.save(newNotificationTemplate);

        return newNotificationTemplate.getId();
    }

    public NotificationTemplate getNotificationTemplate(ObjectId id) {
        return notificationTemplateRepository.findById(id)
                .filter(it ->!it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.NOTIFICATION_TEMPLATE_NOT_FOUND));
    }
}
