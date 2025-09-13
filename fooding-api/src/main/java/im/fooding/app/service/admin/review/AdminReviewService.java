package im.fooding.app.service.admin.review;

import im.fooding.app.dto.request.admin.review.AdminCreateReviewRequest;
import im.fooding.app.dto.request.admin.review.AdminReviewRequest;
import im.fooding.app.dto.request.admin.review.AdminUpdateReviewRequest;
import im.fooding.app.dto.response.admin.review.AdminReviewResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewScore;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.review.ReviewService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
    private final ReviewService reviewService;
    private final StoreService storeService;
    private final UserService userService;

    public PageResponse<AdminReviewResponse> list( AdminReviewRequest request ){
        System.out.println( "ID: " + request.getStoreId() );
        Page<Review> result = reviewService.list( request.getStoreId(), request.getPageable() );
        return PageResponse.of( result.map(AdminReviewResponse::of).stream().toList(), PageInfo.of( result ) );
    }

    public AdminReviewResponse findById( Long reviewId ){
        return AdminReviewResponse.of( reviewService.findById( reviewId ) );
    }

    @Transactional
    public void update( Long id, AdminUpdateReviewRequest request){
        Review review = reviewService.findById( id );
        float totalScore = ( request.getMoodScore() + request.getTasteScore() + request.getServiceScore() ) / 3;
        ReviewScore score = ReviewScore.builder()
                                .mood( request.getMoodScore() )
                                .taste( request.getTasteScore() )
                                .service( request.getServiceScore() )
                                .total( totalScore )
                                .build();
        review.update( request.getContent(), request.getVisitPurposeType(), score );
    }

    @Transactional
    public void create(AdminCreateReviewRequest request){
        ReviewScore score = ReviewScore.builder()
                .mood(request.getMoodScore())
                .taste(request.getTasteScore())
                .service(request.getServiceScore())
                .total(request.getTotalScore())
                .build();
        Review review = Review.builder()
                .store( storeService.findById(request.getStoreId()) )
                .writer( userService.findById(request.getWriterId()) )
                .score( score )
                .content( request.getContent() )
                .visitPurposeType( request.getVisitPurposeType() )
                .build();
        reviewService.create( review );

        // 리뷰 카운트 추가
        Store store = storeService.findById( request.getStoreId() );
        storeService.increaseReviewCount( store );
    }

    @Transactional
    public void delete(Long id, Long deletedBy){
        Review review = reviewService.findById( id );
        // 리뷰 카운트 감소
        Store store = storeService.findById( review.getStore().getId() );
        storeService.decreaseReviewCount( store );
        // 리뷰 삭제
        reviewService.delete( id, deletedBy );
    }
}
