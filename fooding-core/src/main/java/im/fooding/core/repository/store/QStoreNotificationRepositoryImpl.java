package im.fooding.core.repository.store;

import static im.fooding.core.model.store.QStoreNotification.storeNotification;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.StoreNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QStoreNotificationRepositoryImpl implements QStoreNotificationRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<StoreNotification> list(Pageable pageable) {
        BooleanExpression whereClause = storeNotification.deleted.isFalse();

        var content = query
                .selectFrom(storeNotification)
                .where(whereClause)
                .orderBy(storeNotification.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = query
                .select(storeNotification.count())
                .from(storeNotification)
                .where(whereClause)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
}
