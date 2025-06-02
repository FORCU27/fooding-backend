package im.fooding.core.repository.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewSortType;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QReviewRepository {

    Page<Review> list(
            Long storeId,
            Pageable pageable
    );
}
