package im.fooding.core.repository.waiting;

import java.time.LocalDate;

public interface QStoreWaitingRepository {

    long countCreatedOn(LocalDate date);
}
