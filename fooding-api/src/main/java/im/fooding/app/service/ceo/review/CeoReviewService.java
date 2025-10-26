package im.fooding.app.service.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoCreateReviewRequest;
import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.request.ceo.review.CeoUpdateReviewRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoReviewService {
    private final ReviewService reviewService;
    private final UserService userService;
    private final StoreService storeService;

    public PageResponse<CeoReviewResponse> list(CeoReviewRequest request){
        Store store = storeService.findById( request.getStoreId() );
        Page<Review> result = reviewService.list( store, request.getPageable() );
        List<CeoReviewResponse> reviewList = result.map( review -> {
            CeoReviewResponse temp = CeoReviewResponse.of( review );
            User reviewWriter = userService.findById( review.getWriter().getId() );
            int writerReviewCount = reviewService.getReviewCount( reviewWriter.getId() );
            temp.setWriterReviewCount( writerReviewCount );
            return temp;
        }).stream().toList();
        return PageResponse.of( reviewList, PageInfo.of( result ) );
    }

    @Transactional( readOnly = false )
    public void createReview( CeoCreateReviewRequest request, long userId ){
        Store store = storeService.findById( request.getStoreId() );
        User user = userService.findById( userId );
        Review parent = reviewService.findById( request.getReviewId() );
        ReviewScore dummyScore = ReviewScore.builder()
                .mood( 0 )
                .service( 0 )
                .taste( 0 )
                .total( 0 )
                .build();

        Review review = Review.builder()
                .store( store )
                .writer( user )
                .parent( parent )
                .score( dummyScore )
                .content( request.getContent() )
                .visitPurposeType( VisitPurposeType.DUMMY )
                .build();
        reviewService.create( review );
    }

    @Transactional( readOnly = false )
    public void updateReview( CeoUpdateReviewRequest request ){
        Review review = reviewService.findById( request.getId() );
        review.updateComment( request.getContent() );
    }
}
