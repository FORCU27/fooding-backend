package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QWaitingSetting.waitingSetting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
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
                                waitingSetting.isActive.isTrue(),
                                waitingSetting.deleted.isFalse()
                        )
                        .fetchOne()
        );
    }
}
