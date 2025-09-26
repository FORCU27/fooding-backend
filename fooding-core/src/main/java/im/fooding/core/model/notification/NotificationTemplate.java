package im.fooding.core.model.notification;

import im.fooding.core.model.BaseDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notification_templates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NotificationTemplate extends BaseDocument {

    @Id
    private ObjectId id;

    @Indexed
    private Type type;

    private String subject;
    private String content;

    public enum Type {
        WaitingCreatedEmail,
        WaitingCreatedSms,
        RewardEarnSms,
        RewardUseSms
    }

    public NotificationTemplate(Type type, String subject, String content) {
        this.type = type;
        this.subject = subject;
        this.content = content;
    }

    public void updateType(Type type) {
        this.type = type;
    }

    public void updateSubject(String subject) {
        this.subject = subject;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
