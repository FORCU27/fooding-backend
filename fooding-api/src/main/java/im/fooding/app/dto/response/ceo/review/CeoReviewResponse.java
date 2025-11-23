package im.fooding.app.dto.response.ceo.review;

import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewImage;
import im.fooding.core.model.review.VisitPurposeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CeoReviewResponse {
    private Long id;
    private Long storeId;
    private Long writerId;
    private String writerName;
    private String writerProfileImage;
    private LocalDateTime createdAt;
    private String content;
    private int reviewCount;
    private VisitPurposeType visitPurposeType;
    @Schema(description = "답글 목록", type = "array", example = "[Review]")
    private List<CeoReviewResponse> replies;
    private List<String> imageUrls;
    private float totalScore;
    private float tasteScore;
    private float moodScore;
    private float serviceScore;
    private Long likeCount;

    public static CeoReviewResponse of(
            Review review,
            List<ReviewImage> images,
            Long likeCount
    ){
        return CeoReviewResponse.builder()
                .id( review.getId() )
                .storeId( review.getStore().getId() )
                .writerId( review.getWriter().getId() )
                .writerName( review.getWriter().getName() )
                .writerProfileImage( review.getWriter().getProfileImage() )
                .imageUrls(images.stream()
                        .map(ReviewImage::getImageUrl)
                        .collect(Collectors.toList()))
                .likeCount( likeCount )
                .createdAt( review.getCreatedAt() )
                .content( review.getContent() )
                .visitPurposeType( review.getVisitPurposeType() )
                .replies( new ArrayList<>() )
                .totalScore( review.getScore().getTotal() )
                .tasteScore( review.getScore().getTaste() )
                .moodScore( review.getScore().getMood() )
                .serviceScore( review.getScore().getService() )
                .build();
    }
    public void setReviewCount( int reviewCount ) { this.reviewCount = reviewCount; }
    public void addReply( CeoReviewResponse reply ) { this.replies.add( reply ); }
}
