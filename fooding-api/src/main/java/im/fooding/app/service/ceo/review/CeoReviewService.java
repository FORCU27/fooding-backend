package im.fooding.app.service.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoReplyRequest;
import im.fooding.app.dto.request.ceo.review.CeoReplyUpdateRequest;
import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.response.ceo.review.CeoReviewResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewScore;
import im.fooding.core.model.review.VisitPurposeType;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.review.ReviewService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoReviewService {
    private final ReviewService reviewService;
    private final StoreService storeService;
    private final UserService userService;

    public PageResponse<CeoReviewResponse> list(CeoReviewRequest request){
        // 최상위 리뷰만 가져옴
        Page<Review> result = reviewService.list( request.getStoreId(), null, Long.MIN_VALUE, request.getPageable() );
        // Store의 CEO ID 조회
        Store store = storeService.findById( request.getStoreId() );
        List<CeoReviewResponse> reviewList = result.map( review -> {
            // 각 최상위 리뷰에 달린 하위 리뷰를 가져옴
            CeoReviewResponse temp = CeoReviewResponse.of( review );
            Review ceoReply = reviewService.findCeoReply( store, store.getOwner(), review );
            if( ceoReply != null ){
                CeoReviewResponse reply = CeoReviewResponse.of( ceoReply );
                temp.addReply( reply );
            }
            return temp;
        }).stream().toList();
        return PageResponse.of( reviewList, PageInfo.of( result ) );
    }

    @Transactional(readOnly = false)
    public void reply( long reviewId, CeoReplyRequest request ){
        Review review = reviewService.findById( reviewId );
        ReviewScore temp = ReviewScore.builder()
                .taste(0)
                .mood(0)
                .service(0)
                .total(0)
                .build();
        Review.ReviewBuilder builder = Review.builder()
                .store( review.getStore() )
                .score( temp )
                .content( request.getContent() )
                .visitPurposeType( VisitPurposeType.DUMMY );
        if( request.getUserId() != null ){
            User user = userService.findById( request.getUserId() );
            builder.writer( user );
        }
        Review reply = builder.build();
        reply.setParent( review );
        reviewService.create( reply );
    }

    @Transactional(readOnly = false)
    public void updateReply(long reviewId, CeoReplyUpdateRequest request){
        Review review = reviewService.findById( reviewId );
        review.update( request.getContent(), null, null );
    }

    @Transactional(readOnly = false)
    public void deleteReply(long reviewId, long deletedBy){
        Review review = reviewService.findById( reviewId );
        review.delete( deletedBy );
    }
}
