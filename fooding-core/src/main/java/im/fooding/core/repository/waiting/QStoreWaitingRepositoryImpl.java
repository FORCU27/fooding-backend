package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QStoreWaiting.storeWaiting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@Repository

@RequiredArgsConstructor
public class QStoreWaitingRepositoryImpl implements QStoreWaitingRepository {

    private final JPAQueryFactory query;

    @Override
    public long countCreatedOn(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        return query
                .selectFrom(storeWaiting)
                .where(
                        storeWaiting.createdAt.between(start, end),
                        storeWaiting.deleted.isFalse()
                )
                .fetch().size();
    }

    @Override
    public Page<StoreWaiting> findAllWithFilter(StoreWaitingFilter filter, Pageable pageable) {
        List<StoreWaiting> results = query
                .select(storeWaiting)
                .from(storeWaiting)
                .where(
                        storeIdEq(filter.storeId()),
                        statusEq(filter.status()),
                        storeWaiting.deleted.isFalse()
                )
                .orderBy(storeWaiting.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<StoreWaiting> countQuery = query
                .select(storeWaiting)
                .from(storeWaiting)
                .where(
                        storeIdEq(filter.storeId()),
                        statusEq(filter.status()),
                        storeWaiting.deleted.isFalse()
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private BooleanExpression storeIdEq(Long storeId) {
        return storeId != null ? storeWaiting.store.id.eq(storeId) : null;
    }

    private BooleanExpression statusEq(StoreWaitingStatus status) {
        return status != null ? storeWaiting.status.eq(status) : null;
    }
}
