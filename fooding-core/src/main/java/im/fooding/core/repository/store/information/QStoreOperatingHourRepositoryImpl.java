package im.fooding.core.repository.store.information;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.information.StoreOperatingHour;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

import static im.fooding.core.model.store.information.QStoreDailyBreakTime.storeDailyBreakTime;
import static im.fooding.core.model.store.information.QStoreDailyOperatingTime.storeDailyOperatingTime;
import static im.fooding.core.model.store.information.QStoreOperatingHour.storeOperatingHour;

@RequiredArgsConstructor
public class QStoreOperatingHourRepositoryImpl implements QStoreOperatingHourRepository {
    private final JPAQueryFactory query;

    @Override
    public List<StoreOperatingHour> findByIdsInOperatingTime(List<Long> storeIds) {
        return query.selectFrom(storeOperatingHour)
                .innerJoin(storeOperatingHour.dailyOperatingTimes, storeDailyOperatingTime)
                .innerJoin(storeOperatingHour.dailyBreakTimes, storeDailyBreakTime)
                .where(
                        storeOperatingHour.store.id.in(storeIds),
                        isDailyOperatingTimesDeleted(),
                        isDailyBreakTimesDeleted(),
                        storeOperatingHour.deleted.isFalse()
                ).fetch();
    }

    private BooleanExpression isDailyOperatingTimesDeleted() {
        return storeDailyOperatingTime.id.isNull().or(storeDailyOperatingTime.deleted.isFalse());
    }

    private BooleanExpression isDailyBreakTimesDeleted() {
        return storeDailyBreakTime.id.isNull().or(storeDailyBreakTime.deleted.isFalse());
    }
}
