package im.fooding.core.repository.plan;

import im.fooding.core.model.plan.Plan;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanRepository extends MongoRepository<Plan, ObjectId> {

    Page<Plan> findAllByUserIdAndDeletedFalse(long userId, Pageable pageable);
}
