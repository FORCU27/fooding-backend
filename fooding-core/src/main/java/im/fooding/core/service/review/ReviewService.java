package im.fooding.core.service.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewSortType;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.review.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * * 리뷰 목록 조회
     * @param storeId
     * @param pageable
     * @param sortType
     * @param sortDirection
     * @return
     */
    public Page<Review> list(
            Long storeId,
            Pageable pageable,
            ReviewSortType sortType,
            SortDirection sortDirection
    ) {
        return reviewRepository.list(storeId, pageable, sortType, sortDirection);
    }

    public List<Review> list(Store store) {
        return reviewRepository.findAllByStore(store);
    }

    /**
     * * 리뷰 생성
     * @param review
     */
    public void create(Review review) {
        reviewRepository.save(review);
    }
}
