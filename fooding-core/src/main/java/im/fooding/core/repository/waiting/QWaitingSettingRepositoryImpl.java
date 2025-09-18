package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QWaitingSetting.waitingSetting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QWaitingSettingRepositoryImpl implements QWaitingSettingRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<WaitingSetting> findActive(Store store) {
        return Optional.ofNullable(
                query
                        .selectFrom(waitingSetting)
                        .where(
                                waitingSetting.storeService.store.eq(store),
                                waitingSetting.isActive.isTrue()
                        )
                        .fetchOne()
        );
    }

    @Override
    public Page<WaitingSetting> list(Long storeServiceId, Boolean isActive, Pageable pageable) {
        BooleanExpression whereClause = waitingSetting.deleted.isFalse();

        if (storeServiceId != null) {
            whereClause = whereClause.and(waitingSetting.storeService.id.eq(storeServiceId));
        }
        if (isActive != null) {
            whereClause = whereClause.and(waitingSetting.isActive.eq(isActive));
        }

        var content = query
                .selectFrom(waitingSetting)
                .where(whereClause)
                .orderBy(waitingSetting.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = query
                .select(waitingSetting.count())
                .from(waitingSetting)
                .where(whereClause)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    @Override
    public Page<WaitingSetting> list(WaitingSettingFilter filter, Pageable pageable) {
        BooleanExpression whereClause = waitingSetting.deleted.isFalse();

        if (filter.storeServiceId() != null) {
            whereClause = whereClause.and(waitingSetting.storeService.id.eq(filter.storeServiceId()));
        }
        if (filter.status() != null) {
            whereClause = whereClause.and(waitingSetting.status.eq(filter.status()));
        }

        var content = query
                .selectFrom(waitingSetting)
                .where(whereClause)
                .orderBy(waitingSetting.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = query
                .select(waitingSetting.count())
                .from(waitingSetting)
                .where(whereClause)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }
}
