package im.fooding.app.dto.response.user.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewImage;
import im.fooding.core.model.review.ReviewScore;
import im.fooding.core.model.review.VisitPurposeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserStoreReviewResponse {
    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;

    @Schema(description = "리뷰 관련 사진 이미지 URL", example = "https://example.com/image.jpg")
    private List<String> imageUrls;

    @Schema(description = "리뷰 내용", example = "정말 맛있어요!")
    private String content;

    @Schema(description = "리뷰 평점", example = "4.5")
    private ReviewScore score;

    @Schema(description = "방문 목적", example = "데이트")
    private VisitPurposeType purpose;

    @Schema(description = "리뷰 좋아요 수", example = "10")
    private Long likeCount;

    @Schema(description = "리뷰 작성일", example = "2023-10-01")
    private LocalDateTime createdAt;

    @Schema(description = "리뷰 수정일", example = "2023-10-02")
    private LocalDateTime updatedAt;

    @Schema(description = "예약 ID", nullable = true)
    private ObjectId planId;

    @Builder
    public UserStoreReviewResponse(
        Long reviewId,
        List<String> imageUrls,
        String content,
        ReviewScore score,
        VisitPurposeType purpose,
        Long likeCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ObjectId planId
    ){
        this.reviewId = reviewId;
        this.imageUrls = imageUrls;
        this.content = content;
        this.score = score;
        this.purpose = purpose;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.planId = planId;
    }

    public static UserStoreReviewResponse of(
            Review review,
            List<ReviewImage> images,
            Long likeCount,
            ObjectId planId
    ){
        UserStoreReviewResponse.UserStoreReviewResponseBuilder result = UserStoreReviewResponse.builder()
                .reviewId( review.getId() )
                .imageUrls(images.stream()
                        .map(ReviewImage::getImageUrl)
                        .collect(Collectors.toList()))
                .content( review.getContent() )
                .score( review.getScore() )
                .purpose( review.getVisitPurposeType() )
                .likeCount( likeCount )
                .planId( planId );
        return result.build();
    }
}
