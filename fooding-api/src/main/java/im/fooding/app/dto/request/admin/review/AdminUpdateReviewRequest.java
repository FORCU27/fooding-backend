package im.fooding.app.dto.request.admin.review;

import im.fooding.core.model.review.VisitPurposeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUpdateReviewRequest {
    private float serviceScore;
    private float moodScore;
    private float tasteScore;
    private float totalScore;

    private String content;

    private VisitPurposeType visitPurposeType;
}
