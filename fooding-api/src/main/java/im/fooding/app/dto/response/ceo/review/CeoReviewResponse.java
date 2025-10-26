package im.fooding.app.dto.response.ceo.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.VisitPurposeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CeoReviewResponse {
    private Long id;
    private Long storeId;
    private String content;
    private VisitPurposeType visitPurposeType;

    private Long parentId;
    private Long writerId;

    private String writerName;
    private int writerReviewCount;
    private LocalDateTime createdAt;

    private float totalScore;
    private float tasteScore;
    private float moodScore;
    private float serviceScore;

    public void setWriterReviewCount( int count ) { this.writerReviewCount = count; }

    public static CeoReviewResponse of( Review review ){
        CeoReviewResponseBuilder builder = CeoReviewResponse.builder()
                .id( review.getId() )
                .storeId( review.getStore().getId() )
                .writerId( review.getWriter().getId() )
                .content( review.getContent() )
                .visitPurposeType( review.getVisitPurposeType() )
                .totalScore( review.getScore().getTotal() )
                .tasteScore( review.getScore().getTaste() )
                .moodScore( review.getScore().getMood() )
                .serviceScore( review.getScore().getService() )
                .writerName( review.getWriter().getName() )
                .createdAt( review.getCreatedAt() );

        if( review.getParent() != null ) builder.parentId( review.getParent().getId() );
        return builder.build();
    }
}
