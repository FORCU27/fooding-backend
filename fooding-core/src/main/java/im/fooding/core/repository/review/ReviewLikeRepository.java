package im.fooding.core.repository.review;

import im.fooding.core.model.review.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long>, QReviewLikeRepository {

}
