package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QWaiting.waiting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QWaitingRepositoryImpl implements QWaitingRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Waiting> list(Long storeId, WaitingStatus status, Pageable pageable) {
        var predicate = waiting.deleted.isFalse();
        if (storeId != null) {
            predicate = predicate.and(waiting.store.id.eq(storeId));
        }
        if (status != null) {
            predicate = predicate.and(waiting.status.eq(status));
        }

        var base = queryFactory
                .selectFrom(waiting)
                .where(predicate)
                .orderBy(waiting.id.desc());

        List<Waiting> results = base
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(waiting.count())
                .from(waiting)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0L);
    }
}

