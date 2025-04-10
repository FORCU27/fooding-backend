package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QStoreWaiting.storeWaiting;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public Page<StoreWaiting> findAllByStoreIdAndStatus(long storeId, StoreWaitingStatus status, Pageable pageable) {
        List<StoreWaiting> results = query
                .select(storeWaiting)
                .from(storeWaiting)
                .where(
                        storeWaiting.store.id.eq(storeId),
                        storeWaiting.status.eq(status)
                )
                .orderBy(storeWaiting.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<StoreWaiting> countQuery = query
                .select(storeWaiting)
                .from(storeWaiting)
                .where(
                        storeWaiting.store.id.eq(storeId),
                        storeWaiting.status.eq(status)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }
}
