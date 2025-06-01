package im.fooding.app.dto.response.admin.review;

import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.VisitPurposeType;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class AdminReviewResponse {
    private Long id;

    private Long writerId;

    private Long storeId;

    private float serviceScore;
    private float moodScore;
    private float tasteScore;
    private float totalScore;

    private String content;

    private VisitPurposeType visitPurposeType;

    public static AdminReviewResponse of(Review review){
        return AdminReviewResponse.builder()
                .id( review.getId() )
                .writerId( review.getWriter().getId() )
                .storeId( review.getStore().getId() )
                .serviceScore( review.getScore().getService() )
                .moodScore( review.getScore().getMood() )
                .tasteScore( review.getScore().getTaste() )
                .totalScore( review.getScore().getTotal() )
                .content( review.getContent() )
                .visitPurposeType( review.getVisitPurposeType() )
                .build();

    }
}
