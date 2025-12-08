package im.fooding.core.service.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewLike;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.review.ReviewLikeRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;

    /**
     *
     * @param reviewIds
     * @return
     */
    public Map<Long, Long> list(List<Long> reviewIds) {
       return reviewLikeRepository.list(reviewIds);
    }

    public List<ReviewLike> findAllByUserId( long userId) { return reviewLikeRepository.findAllByUserIdAndDeletedFalse(userId); }

    public List<ReviewLike> findAllByReviewId( long reviewId ){ return reviewLikeRepository.findAllByReviewId( reviewId ); }

    public ReviewLike findByReviewIdAndUserId( long reviewId, long userId ){ return reviewLikeRepository.findByReviewIdAndUserIdAndDeletedFalse( reviewId, userId ).orElse( null ); }

    public void create(Review review, User user){
        ReviewLike reviewLike = ReviewLike.builder()
                .review( review )
                .user( user )
                .build();
        reviewLikeRepository.save( reviewLike );
    }
}
