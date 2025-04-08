package im.fooding.core.repository.waiting;

import static im.fooding.core.model.waiting.QStoreWaiting.storeWaiting;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QStoreWaitingRepositoryImpl implements QStoreWaitingRepository {

    private final JPAQueryFactory query;

    @Override
    public long countTodayCreated() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        return query
                .selectFrom(storeWaiting)
                .where(storeWaiting.createdAt.between(start, end))
                .fetch().size();
    }
}
