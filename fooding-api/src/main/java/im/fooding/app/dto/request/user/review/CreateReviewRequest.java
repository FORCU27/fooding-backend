package im.fooding.app.dto.request.user.review;

import im.fooding.core.model.review.VisitPurposeType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateReviewRequest {
    @NotNull
    private Long storeId;

    @NotNull
    private Long userId;

    @NotNull
    private String content;

    @NotNull
    private VisitPurposeType visitPurpose;

    private List<String> imageUrls;

    // ReviewScore를 위한 값
    @NotNull
    private float total;
    @NotNull
    private float taste;
    @NotNull
    private float mood;
    @NotNull
    private float service;
}
