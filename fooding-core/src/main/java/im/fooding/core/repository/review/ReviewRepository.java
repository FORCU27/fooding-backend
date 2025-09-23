package im.fooding.core.repository.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.store.Store;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, QReviewRepository {

    Page<Review> findAllByStore(Store store, Pageable pageable);
    List<Review> findAllByWriterId( long writerId );
}
