package im.fooding.core.repository.review;

import im.fooding.core.model.review.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long>, QReviewLikeRepository {
    List<ReviewLike> findAllByReviewId( long reviewId );
    Optional<ReviewLike> findByReviewIdAndUserIdAndDeletedFalse( long reviewId, long userId );
}
