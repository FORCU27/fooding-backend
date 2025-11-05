package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreStatistics;
import java.time.LocalDate;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreStatisticsRepository extends MongoRepository<StoreStatistics, ObjectId> {

    Optional<StoreStatistics> findByStoreIdAndDate(long storeId, LocalDate date);
}
