package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QStoreWaiting.storeWaiting;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class QStoreWaitingRepositoryImpl implements QStoreWaitingRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<StoreWaiting> findAllWithFilter(StoreWaitingFilter filter, Pageable pageable) {
        List<StoreWaiting> results = query
                .select(storeWaiting)
                .from(storeWaiting)
                .where(
                        storeIdEq(filter.storeId()),
                        statusEq(filter.status())
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
                        statusEq(filter.status())
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
