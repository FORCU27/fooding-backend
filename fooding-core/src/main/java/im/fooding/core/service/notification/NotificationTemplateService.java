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

    public ObjectId createWaitingRegisterTemplate(
            String store,
            int totalPersonCount,
            int order,
            int waitingNumber,
            NotificationTemplate.Type type,
            String contact
    ) {
        String subject = "푸딩 웨이팅 등록 완료";
        String content = """
            안녕하세요 고객님!
            [%s]에 웨이팅이 등록되었습니다. 입장 순서가 되면 안내 메시지를 보내드릴게요.

            - 인원: %d명
            - 순서: %d번째
            - 웨이팅번호: %d

            [주의사항]
            - 차례가 되었을 때 현장에 없는 경우 입장이 취소될 수 있습니다.
            """.formatted(store, totalPersonCount, order, waitingNumber);

        return createNotificationTemplate(
                subject,
                content,
                type,
                contact
        );
    }

    public ObjectId createNotificationTemplate(
            String subject,
            String content,
            NotificationTemplate.Type type,
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
