package im.fooding.core.repository.banner;

import im.fooding.core.model.banner.Banner;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends MongoRepository<Banner, ObjectId>, QBannerRepository {
}
