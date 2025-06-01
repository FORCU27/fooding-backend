package im.fooding.app.dto.request.ceo.review;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoReviewRequest extends BasicSearch {
    @NotEmpty
    @Schema( description = "CEO ID" )
    private Long ceoId;

    @Schema( description = "매장 ID" )
    private Long storeId;
}
