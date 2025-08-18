package im.fooding.core.model.notification;

import im.fooding.core.model.BaseDocument;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notification_templates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NotificationTemplate extends BaseDocument {

    @Id
    private ObjectId id;

    private String subject;
    private String content;
    private Type type;
    private String contact;

    public enum Type {
        WaitingCreatedEmail,
        WaitingCreatedSms,
    }

    @Builder
    public NotificationTemplate(String subject, String content, Type type, String contact) {
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.contact = contact;
    }
}
