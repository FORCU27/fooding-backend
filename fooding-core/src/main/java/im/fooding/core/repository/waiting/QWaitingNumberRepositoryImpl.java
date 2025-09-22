package im.fooding.core.repository.waiting;

import com.querydsl.core.types.dsl.BooleanExpression;
import im.fooding.core.model.banner.Banner;
import im.fooding.core.model.waiting.QWaitingNumberGenerator;
import im.fooding.core.model.waiting.WaitingNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@RequiredArgsConstructor
public class QWaitingNumberRepositoryImpl implements QWaitingNumberRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public int issueNumberByStoreId(long storeId) {
        QWaitingNumberGenerator waitingNumberGenerator = QWaitingNumberGenerator.waitingNumberGenerator;

        BooleanExpression condition = waitingNumberGenerator.deleted.isFalse();

        condition.and(waitingNumberGenerator.storeId.eq(storeId));

        WaitingNumberGenerator updated = mongoTemplate.findAndModify(
                Query.query(Criteria.where("storeId").is(storeId)),
                new Update().inc("lastAssignedNumber", 1),
                FindAndModifyOptions.options().returnNew(true),
                WaitingNumberGenerator.class
        );

        return updated.getLastAssignedNumber();
    }
}
