package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.WaitingNumberGenerator;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaitingNumberGeneratorRepository extends MongoRepository<WaitingNumberGenerator, ObjectId> {

    Optional<WaitingNumberGenerator> findByStoreId(long storeId);
}
