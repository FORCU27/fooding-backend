package im.fooding.core.repository.plan;

import com.querydsl.core.types.dsl.BooleanExpression;
import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.plan.QPlan;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.SpringDataMongodbQuery;

@RequiredArgsConstructor
public class QPlanRepositoryImpl implements QPlanRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Plan> list(Pageable pageable) {
        return list(PlanFilter.non(), pageable);
    }

    @Override
    public Page<Plan> list(PlanFilter filter, Pageable pageable) {
        QPlan plan = QPlan.plan;


        // 조건 생성
        BooleanExpression condition = plan.deleted.isFalse();
        if (filter.userId() != null) {
            condition = condition.and(plan.userId.eq(filter.userId()));
        }
        if (filter.visitStatus() != null) {
            condition = condition.and(plan.visitStatus.eq(filter.visitStatus()));
        }

        SpringDataMongodbQuery<Plan> query = new SpringDataMongodbQuery<>(mongoTemplate, Plan.class);
        query = query.where(condition);

        long total = query.fetchCount();
        query = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 결과 조회
        List<Plan> content = query.fetch();

        return new PageImpl<>(content, pageable, total);
    }
}
