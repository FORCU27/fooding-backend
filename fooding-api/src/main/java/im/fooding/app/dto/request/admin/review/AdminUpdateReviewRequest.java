package im.fooding.app.dto.request.admin.review;

import im.fooding.core.model.review.VisitPurposeType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminUpdateReviewRequest {
    private float serviceScore;
    private float moodScore;
    private float tasteScore;
    private float totalScore;

    @NotNull
    private String content;

    private VisitPurposeType visitPurposeType;
}
