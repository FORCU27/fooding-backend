package im.fooding.app.service.ceo.review;

import im.fooding.app.dto.request.ceo.review.CeoReplyRequest;
import im.fooding.app.dto.request.ceo.review.CeoReplyUpdateRequest;
import im.fooding.app.dto.request.ceo.review.CeoReviewRequest;
import im.fooding.app.dto.response.ceo.review.CeoReviewResponse;
import im.fooding.app.dto.response.user.review.UserReviewResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.review.*;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.plan.PlanService;
import im.fooding.core.service.review.ReviewImageService;
import im.fooding.core.service.review.ReviewLikeService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.review.ReviewService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoReviewService {
    private final ReviewService reviewService;
    private final StoreService storeService;
    private final UserService userService;
    private final ReviewImageService reviewImageService;
    private final ReviewLikeService reviewLikeService;

    public PageResponse<CeoReviewResponse> list(CeoReviewRequest request){
//        // 최상위 리뷰만 가져옴
//        Page<Review> result = reviewService.list( request.getStoreId(), null, Long.MIN_VALUE, request.getPageable() );
//        // Store의 CEO ID 조회
//        Store store = storeService.findById( request.getStoreId() );
//        List<CeoReviewResponse> reviewList = result.map( review -> {
//            // 각 최상위 리뷰에 달린 하위 리뷰를 가져옴
//            CeoReviewResponse temp = CeoReviewResponse.of( review );
//            temp.setReviewCount(reviewService.getReviewCount( review.getWriter() ) );
//            Review ceoReply = reviewService.findCeoReply( store, store.getOwner(), review );
//            if( ceoReply != null ){
//                CeoReviewResponse reply = CeoReviewResponse.of( ceoReply );
//                temp.addReply( reply );
//            }
//            return temp;
//        }).stream().toList();
//        return PageResponse.of( reviewList, PageInfo.of( result ) );
        Page<Review> result = reviewService.list( request.getStoreId(), null, Long.MIN_VALUE, request.getPageable() );
        Store store = storeService.findById( request.getStoreId() );
        List<Long> reviewIds = getReviewIds(result.getContent());

        Map<Long, List<ReviewImage>> imageMap = getReviewImageMap(reviewIds);
        Map<Long, Long> likeCountMap = getReviewLikeMap(reviewIds);

        List<CeoReviewResponse> content = result.getContent().stream()
                .map(review -> {
                    CeoReviewResponse temp = CeoReviewResponse.of(
                            review,
                            imageMap.getOrDefault(review.getId(), List.of()),
                            likeCountMap.getOrDefault(review.getId(), 0L)
                    );
                    temp.setReviewCount( reviewService.getReviewCount( review.getWriter() ) );
                    Review ceoReply = reviewService.findCeoReply( store, store.getOwner(), review );
                    if( ceoReply != null ){
                        CeoReviewResponse reply = CeoReviewResponse.of( ceoReply, new ArrayList<>(), 0L );
                        temp.addReply( reply );
                    }
                    return temp;
                })
                .toList();
        return PageResponse.of( content, PageInfo.of( result ) );
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

    @Transactional(readOnly = false)
    public void reply( long reviewId, CeoReplyRequest request ){
        Review review = reviewService.findById( reviewId );
        User user = userService.findById( request.getUserId() );

        // 이미 해당 리뷰에 CEO가 답글을 달았는지 확인하기
        Review alreadyReply = reviewService.findCeoReply( review.getStore(), user, review );
        if( alreadyReply != null ) throw new ApiException(ErrorCode.REPLY_ALREADY_EXISTED);

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
                .writer( user )
                .visitPurposeType( VisitPurposeType.BUSINESS );
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
