package im.fooding.core.repository.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.store.Store;
import java.util.List;
import java.util.Optional;

import im.fooding.core.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, QReviewRepository {

    Page<Review> findAllByStore(Store store, Pageable pageable);
    Optional<Review> findByStoreAndWriterAndParent( Store store, User user, Review parent );
    List<Review> findAllByWriter( User writer );
}
