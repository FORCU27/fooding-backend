package im.fooding.app.dto.response.user.review;

import im.fooding.core.model.review.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class UserReviewDetailResponse {
    // 리뷰에 대한 정보
    private float totalScore;
    private List<String> imageUrls;
    private int likeCount;

    // 작성자에 대한 정보
    private String writerName;
    private LocalDateTime createdAt;
    private int writerReviewCount;
    private String profileImageUrl;

    public static UserReviewDetailResponse of(Review review){
        return UserReviewDetailResponse.builder()
                .totalScore( review.getScore().getTotal() )
                .writerName( review.getWriter().getName() )
                .createdAt( review.getCreatedAt() )
                .profileImageUrl( review.getWriter().getProfileImage() )
                .build();
    }
    public void setImageUrls( List<String> imageUrls ){ this.imageUrls = imageUrls; }
    public void setLikeCount( int count ){ this.likeCount = count; }
    public void setWriterReviewCount( int count ){ this.writerReviewCount = count; }
}
