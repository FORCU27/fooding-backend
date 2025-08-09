package im.fooding.app.service.user.review;

import im.fooding.app.dto.request.user.review.CreateReviewRequest;
import im.fooding.app.dto.request.user.review.UserRetrieveReviewRequest;
import im.fooding.app.dto.response.user.review.UserReviewResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewImage;
import im.fooding.core.model.review.ReviewScore;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.review.ReviewImageService;
import im.fooding.core.service.review.ReviewLikeService;
import im.fooding.core.service.review.ReviewService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Transactional( readOnly = true )
    public PageResponse<UserReviewResponse> list(Long storeId, UserRetrieveReviewRequest request) {
        Page<Review> reviewPage = reviewService.list(storeId, request.getPageable());

        List<Long> reviewIds = getReviewIds(reviewPage.getContent());

        Map<Long, List<ReviewImage>> imageMap = getReviewImageMap(reviewIds);
        Map<Long, Long> likeCountMap = getReviewLikeMap(reviewIds);

        List<UserReviewResponse> content = reviewPage.getContent().stream()
                .map(review -> UserReviewResponse.of(
                        review,
                        imageMap.getOrDefault(review.getId(), List.of()),
                        likeCountMap.getOrDefault(review.getId(), 0L)
                ))
                .toList();

        return PageResponse.of(content, PageInfo.of(reviewPage));
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
        ReviewScore score = ReviewScore.builder()
                .mood( request.getMood() )
                .service( request.getService() )
                .taste( request.getTaste() )
                .total( request.getTotal() )
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
}
