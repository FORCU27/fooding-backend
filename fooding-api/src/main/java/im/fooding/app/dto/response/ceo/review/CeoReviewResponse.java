package im.fooding.app.dto.response.ceo.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.VisitPurposeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CeoReviewResponse {
    private Long id;
    private Long storeId;
    private Long writerId;
    private String content;
    private VisitPurposeType visitPurposeType;
    private float totalScore;
    private float tasteScore;
    private float moodScore;
    private float serviceScore;

    public static CeoReviewResponse of( Review review ){
        return CeoReviewResponse.builder()
                .id( review.getId() )
                .storeId( review.getStore().getId() )
                .writerId( review.getWriter().getId() )
                .content( review.getContent() )
                .visitPurposeType( review.getVisitPurposeType() )
                .totalScore( review.getScore().getTotal() )
                .tasteScore( review.getScore().getTaste() )
                .moodScore( review.getScore().getMood() )
                .serviceScore( review.getScore().getService() )
                .build();
    }
}
