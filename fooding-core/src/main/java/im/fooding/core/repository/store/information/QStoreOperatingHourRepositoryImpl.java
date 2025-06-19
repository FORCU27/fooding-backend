package im.fooding.core.repository.store.information;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.information.StoreOperatingHour;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

import static im.fooding.core.model.store.information.QStoreDailyOperatingTime.storeDailyOperatingTime;
import static im.fooding.core.model.store.information.QStoreOperatingHour.storeOperatingHour;

@RequiredArgsConstructor
public class QStoreOperatingHourRepositoryImpl implements QStoreOperatingHourRepository {
    private final JPAQueryFactory query;

    @Override
    public List<StoreOperatingHour> findByIdsInOperatingTime(List<Long> storeIds, DayOfWeek week) {
        return query.selectFrom(storeOperatingHour)
                .innerJoin(storeOperatingHour.dailyOperatingTimes, storeDailyOperatingTime).fetchJoin()
                .where(
                        storeDailyOperatingTime.dayOfWeek.eq(week),
                        storeOperatingHour.store.id.in(storeIds),
                        isDailyOperatingTimesDeleted(),
                        storeOperatingHour.deleted.isFalse()
                ).fetch();
    }

    private BooleanExpression isDailyOperatingTimesDeleted() {
        return storeDailyOperatingTime.id.isNull().or(storeDailyOperatingTime.deleted.isFalse());
    }
}
