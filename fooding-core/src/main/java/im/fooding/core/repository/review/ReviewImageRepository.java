package im.fooding.core.repository.review;

import im.fooding.core.model.review.ReviewImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findAllByReviewIdIn(List<Long> reviewIds);
}
