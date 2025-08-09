package im.fooding.core.service.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewImage;
import im.fooding.core.repository.review.ReviewImageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewImageService {

    private final ReviewImageRepository reviewImageRepository;

    /**
     * 리뷰 이미지 목록 조회
     * @param reviewIds
     * @return
     */
    public List<ReviewImage> list(List<Long> reviewIds) {
        return reviewImageRepository.findAllByReviewIdInAndDeletedFalse(reviewIds);
    }

    /**
     * 리뷰 이미지 업로드
     * @param review
     * @param imageUrls
     * @return
     */
    public void create( Review review, List<String> imageUrls ){
        for( String url : imageUrls ){
            ReviewImage image = ReviewImage.builder()
                    .review( review )
                    .imageUrl( url )
                    .sortOrder( 0 )
                    .build();
            reviewImageRepository.save( image );
        }
    }
}
