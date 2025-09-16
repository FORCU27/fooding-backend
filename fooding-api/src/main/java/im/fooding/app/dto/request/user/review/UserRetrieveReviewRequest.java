package im.fooding.app.dto.request.user.review;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.review.ReviewSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.SortDirection;

@Getter
@Setter
@NoArgsConstructor
public class UserRetrieveReviewRequest extends BasicSearch {

    @NotNull
    @Schema(
            description = "정렬 타입",
            example = "RECENT",
            allowableValues = {"RECENT", "REVIEW"}
    )
    private ReviewSortType sortType;

    @NotNull
    @Schema(
            description = "정렬 순서",
            example = "ASCENDING",
            allowableValues = {"ASCENDING", "DESCENDING"}
    )
    private SortDirection sortDirection;

    private Long writerId;
}
