package im.fooding.app.dto.response.user.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewImage;
import im.fooding.core.model.review.ReviewScore;
import im.fooding.core.model.review.VisitPurposeType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserReviewResponse {

    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;

    @Schema(description = "리뷰 작성자 닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "리뷰 작성자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String profileUrl;

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

    @Schema(description = "작성자의 총 작성 리뷰 수", example = "100")
    private int userReviewCount;

    @Schema(description = "리뷰 작성일", example = "2023-10-01")
    private LocalDateTime createdAt;

    @Schema(description = "리뷰 수정일", example = "2023-10-02")
    private LocalDateTime updatedAt;

    @Builder
    private UserReviewResponse(
            Long reviewId,
            String nickname,
            String profileUrl,
            List<String> imageUrls,
            String content,
            ReviewScore score,
            VisitPurposeType purpose,
            Long likeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            int userReviewCount
    ) {
        this.reviewId = reviewId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.imageUrls = imageUrls;
        this.content = content;
        this.score = score;
        this.purpose = purpose;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userReviewCount = userReviewCount;
    }

    public static UserReviewResponse of(
            Review review,
            List<ReviewImage> images,
            Long likeCount
    ) {
        UserReviewResponse.UserReviewResponseBuilder result = UserReviewResponse.builder()
                .reviewId(review.getId())
                .nickname(review.getWriter().getNickname())
                .profileUrl(review.getWriter().getProfileImage())
                .imageUrls(images.stream()
                        .map(ReviewImage::getImageUrl)
                        .collect(Collectors.toList()))
                .content(review.getContent())
                .score(review.getScore())
                .purpose(review.getVisitPurposeType())
                .likeCount(likeCount)
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt());
        if( review.isBlind() ){
            result.nickname( "블라인드 된 사용자" );
            result.content( "블라인드 된 리뷰입니다." );
            result.profileUrl( "" );
            result.imageUrls( new ArrayList<>() );
        }
        return result.build();
    }
}
