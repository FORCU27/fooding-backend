package im.fooding.core.service.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewImage;
import im.fooding.core.repository.review.ReviewImageRepository;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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

    /**
     * 리뷰 이미지 업데이트
     * @param review
     * @param imageUrls
     * @return
     */
     public void update( Review review, List<String> imageUrls ){
         // 기존 이미지 리스트랑 새로운 이미지 리스트랑 비교해서 삭제된 것은 삭제
         List<Long> reviewIds = new ArrayList<Long>();
         reviewIds.add( review.getId() );
         List<ReviewImage> oldReview = reviewImageRepository.findAllByReviewIdInAndDeletedFalse( reviewIds );
        for( String url : imageUrls ){
            ReviewImage temp = reviewImageRepository.findByImageUrl( url );
            if( temp == null ){
                // 이미지 생성
                ReviewImage image = ReviewImage.builder()
                        .review( review )
                        .imageUrl( url )
                        .sortOrder(0)
                        .build();
                reviewImageRepository.save( image );
            }
            else if(!oldReview.contains(temp)){
                // 이미지 삭제
                temp.delete( review.getWriter().getId() );
            }
        }
     }
}
