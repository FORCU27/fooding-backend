package im.fooding.app.dto.request.ceo.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CeoCreateReviewRequest {
    @NotNull
    @Schema( description = "가게 ID", example = "1" )
    private Long storeId;

    @NotNull
    @Schema( description = "작성자 ID" )
    private Long userId;

    @NotNull
    @Schema( description = "상위 리뷰 ID. CEO는 답글만 달 수 있으므로 필수. 답글인 경우에만 존재. 답글이 아닌 리뷰인 경우 null" )
    private Long reviewId;

    @NotNull
    @Schema( description = "답글 내역" )
    private String content;
}
