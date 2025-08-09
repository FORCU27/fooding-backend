package im.fooding.core.service.review;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
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
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * * 리뷰 목록 조회
     * @param storeId
     * @param pageable
     * @return
     */
    public Page<Review> list(
            Long storeId,
            Pageable pageable
    ) {
        return reviewRepository.list(storeId, pageable);
    }

    public Page<Review> list(Store store, Pageable pageable) {
        return reviewRepository.findAllByStore(store, pageable);
    }

    public Review findById( Long id ){
        return reviewRepository.findById( id ).orElseThrow(
                () -> new ApiException(ErrorCode.REVIEW_NOT_FOUND)
        );
    }

    /**
     * * 리뷰 생성
     * @param review
     */
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    /**
     * * 리뷰 삭제
     * @param id
     * @param deletedBy
     */
    public void delete( Long id, Long deletedBy ){
        Review review = reviewRepository.findById( id ).orElseThrow(
                () -> new ApiException(ErrorCode.REVIEW_NOT_FOUND)
        );
        review.delete( deletedBy );
    }

    /**
     * * 작성자의 리뷰 수
     * @param id
     */
    public int getReviewCount( Long id ){
        return 0;
    }

}
