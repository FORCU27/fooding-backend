package im.fooding.app.dto.request.user.review;

import im.fooding.core.model.review.ReviewScore;
import im.fooding.core.model.review.VisitPurposeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateReviewRequest {
    private String content;
    private VisitPurposeType visitPurposeType;
    private float tasteScore;
    private float moodScore;
    private float serviceScore;
    private List<String> imageUrls;
}
