package im.fooding.core.repository.naverplace;

import im.fooding.core.model.naverplace.NaverPlace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NaverPlaceRepository extends MongoRepository<NaverPlace, Long> {
}
