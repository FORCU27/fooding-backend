package im.fooding.core.repository.notification;

import im.fooding.core.model.notification.NotificationTemplate;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationTemplateRepository extends MongoRepository<NotificationTemplate, ObjectId> {

    Optional<NotificationTemplate> findByType(NotificationTemplate.Type type);
}
