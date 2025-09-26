package im.fooding.app.service.user.review;

import im.fooding.app.dto.request.user.review.CreateReviewRequest;
import im.fooding.app.dto.request.user.review.UpdateReviewRequest;
import im.fooding.app.dto.request.user.review.UserRetrieveReviewRequest;
import im.fooding.app.dto.response.user.review.UserReviewResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewImage;
import im.fooding.core.model.review.ReviewScore;
import im.fooding.core.model.review.ReviewSortType;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.plan.PlanService;
import im.fooding.core.service.review.ReviewImageService;
import im.fooding.core.service.review.ReviewLikeService;
import im.fooding.core.service.review.ReviewService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserReviewService {

    private final ReviewService reviewService;
    private final ReviewImageService reviewImageService;
    private final ReviewLikeService reviewLikeService;
    private final UserService userService;
    private final StoreService storeService;
    private final PlanService planService;

    @Transactional( readOnly = true )
    public PageResponse<UserReviewResponse> list(Long storeId, UserRetrieveReviewRequest request) {
        Sort sortInformation = null;
        if( request.getSortType() == ReviewSortType.RECENT ) {
            Sort.Direction direction = this.getDirection( request.getSortDirection() );
            System.out.println( direction );
            sortInformation = Sort.by( direction, "createdAt" );
        }
        Pageable pageable;
        if( sortInformation != null )pageable = PageRequest.of( request.getPageNum() - 1, request.getPageSize(), sortInformation );
        else pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize() );

        Long writerId = request.getWriterId();
        Page<Review> reviewPage = reviewService.list(storeId, writerId, pageable );

        List<Long> reviewIds = getReviewIds(reviewPage.getContent());

        Map<Long, List<ReviewImage>> imageMap = getReviewImageMap(reviewIds);
        Map<Long, Long> likeCountMap = getReviewLikeMap(reviewIds);

        Plan plan = (writerId != null && storeId != null)
                ? planService.findByUserIdAndStoreId(writerId, storeId)
                : null;

        List<UserReviewResponse> content = reviewPage.getContent().stream()
                .map(review -> UserReviewResponse.of(
                        review,
                        imageMap.getOrDefault(review.getId(), List.of()),
                        likeCountMap.getOrDefault(review.getId(), 0L),
                        plan != null ? plan.getId() : null
                ))
                .toList();

        return PageResponse.of(content, PageInfo.of(reviewPage));
    }
    private Sort.Direction getDirection(SortDirection direction){
        if( direction == SortDirection.ASCENDING ) return Sort.Direction.ASC;
        else if( direction == SortDirection.DESCENDING ) return Sort.Direction.DESC;
        else return null;
    }

    private List<Long> getReviewIds(List<Review> reviews) {
        return reviews.stream()
                .map(Review::getId)
                .toList();
    }

    private Map<Long, List<ReviewImage>> getReviewImageMap(List<Long> reviewIds) {
        return reviewImageService.list(reviewIds).stream()
                .collect(Collectors.groupingBy(image -> image.getReview().getId()));
    }

    private Map<Long, Long> getReviewLikeMap(List<Long> reviewIds) {
        return reviewLikeService.list(reviewIds);
    }

    @Transactional
    public void create(CreateReviewRequest request){
        User user = userService.findById( request.getUserId() );
        Store store = storeService.findById( request.getStoreId() );

        float totalScore = ( request.getMood() + request.getService() + request.getTaste() ) / 3;
        ReviewScore score = ReviewScore.builder()
                .mood( request.getMood() )
                .service( request.getService() )
                .taste( request.getTaste() )
                .total( totalScore )
                .build();
        // 리뷰 추가
        Review review = Review.builder()
                .store( store )
                .writer( user )
                .score( score )
                .content( request.getContent() )
                .visitPurposeType( request.getVisitPurpose() )
                .build();
        Review result = reviewService.create( review );
        // 리뷰 이미지 추가
        reviewImageService.create( result, request.getImageUrls() );
        // 리뷰 수 추가
        storeService.increaseReviewCount( store );
    }

    @Transactional
    public void delete( long id, long deletedBy ){
        reviewService.delete( id, deletedBy );
        Review review = reviewService.findById( id );
        Store store = storeService.findById( review.getStore().getId() );
        storeService.decreaseReviewCount( store );
    }

    @Transactional
    public void update(long id, UpdateReviewRequest request){
        // 리뷰 수정
        float totalScore = ( request.getMoodScore() + request.getServiceScore() + request.getTasteScore() ) / 3;
        ReviewScore score = ReviewScore.builder()
                            .mood( request.getMoodScore() )
                            .service( request.getServiceScore() )
                            .taste( request.getTasteScore() )
                            .total( totalScore )
                            .build();
        reviewService.update( id, request.getContent(), request.getVisitPurposeType(), score );
        // 리뷰 이미지 수정
        Review review = reviewService.findById( id );
        reviewImageService.update( review, request.getImageUrls() );
    }
}
