package im.fooding.core.repository.store.image;

import im.fooding.core.model.store.StorePostImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorePostImageRepository extends JpaRepository<StorePostImage, Long> {

    Optional<StorePostImage> findByStorePostId(long storePostId);

    List<StorePostImage> findAllByStorePostId(long storePostId);

    Optional<StorePostImage> findByImageId(String imageId);
}
