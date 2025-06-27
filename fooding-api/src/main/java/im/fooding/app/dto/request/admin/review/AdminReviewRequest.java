package im.fooding.app.dto.request.admin.review;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.review.VisitPurposeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminReviewRequest extends BasicSearch {
    private Long writerId;
    private Long storeId;
    private float serviceScore;
    private float moodScore;
    private float tasteScore;
    private float totalScore;
    private String content;
    private VisitPurposeType visitPurposeType;
}
