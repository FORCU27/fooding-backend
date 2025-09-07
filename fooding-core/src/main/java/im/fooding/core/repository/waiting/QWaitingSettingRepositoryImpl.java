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
                                waitingSetting.waiting.store.eq(store),
                                waitingSetting.isActive.isTrue()
                        )
                        .fetchOne()
        );
    }

    @Override
    public Page<WaitingSetting> list(Long waitingId, Boolean isActive, Pageable pageable) {
        BooleanExpression whereClause = waitingSetting.deleted.isFalse();

        if (waitingId != null) {
            whereClause = whereClause.and(waitingSetting.waiting.id.eq(waitingId));
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
}
