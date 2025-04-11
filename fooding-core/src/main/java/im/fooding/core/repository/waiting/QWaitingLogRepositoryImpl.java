package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QWaitingLog.waitingLog;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.dto.request.waiting.WaitingLogFilter;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingLogType;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QWaitingLogRepositoryImpl implements QWaitingLogRepository {

    private final JPAQueryFactory query;

    @Override
    public List<WaitingLog> findAllWithFilter(WaitingLogFilter filter) {
        return query
                .select(waitingLog)
                .from(waitingLog)
                .where(
                        storeWaitingIdEq(filter.storeWaitingId()),
                        waitingLogEq(filter.type())
                )
                .orderBy(waitingLog.id.asc())
                .fetch();
    }

    private BooleanExpression storeWaitingIdEq(Long storeWaitingId) {
        return storeWaitingId != null ? waitingLog.storeWaiting.id.eq(storeWaitingId) : null;
    }

    private Predicate waitingLogEq(WaitingLogType type) {
        return type != null ? waitingLog.type.eq(type) : null;
    }
}
