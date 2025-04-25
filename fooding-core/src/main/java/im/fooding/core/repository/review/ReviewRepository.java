package im.fooding.core.repository.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.store.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByStore(Store store);
}
