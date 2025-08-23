package im.fooding.core.service.notification;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.notification.NotificationTemplate;
import im.fooding.core.model.notification.NotificationTemplate.Type;
import im.fooding.core.repository.notification.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationTemplateService {

    private final NotificationTemplateRepository notificationTemplateRepository;

    public ObjectId create(
            String subject,
            String content,
            NotificationTemplate.Type type
    ) {
        NotificationTemplate newNotificationTemplate = new NotificationTemplate(type, subject, content);

        notificationTemplateRepository.save(newNotificationTemplate);

        return newNotificationTemplate.getId();
    }

    public NotificationTemplate getByType(NotificationTemplate.Type type) {
        return notificationTemplateRepository.findByType(type)
                .filter(it ->!it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.NOTIFICATION_TEMPLATE_NOT_FOUND));
    }

    public Page<NotificationTemplate> findAll(Pageable pageable) {
        return notificationTemplateRepository.findAllByDeletedFalse(pageable);
    }

    public NotificationTemplate getById(ObjectId id) {
        return notificationTemplateRepository.findById(id)
                .filter(it ->!it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.NOTIFICATION_TEMPLATE_NOT_FOUND));
    }

    public void update(ObjectId id, Type type, String subject, String content) {
        NotificationTemplate target = getById(id);

        target.updateType(type);
        target.updateSubject(subject);
        target.updateContent(content);

        notificationTemplateRepository.save(target);
    }

    public void delete(ObjectId id, Long deletedBy) {
        NotificationTemplate target = getById(id);

        target.delete(deletedBy);

        notificationTemplateRepository.save(target);
    }
}
