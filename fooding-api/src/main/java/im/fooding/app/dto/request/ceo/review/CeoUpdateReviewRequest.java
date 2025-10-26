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
public class CeoUpdateReviewRequest {
    @NotNull
    @Schema( description = "수정할 답글 내용" )
    private String content;
}
