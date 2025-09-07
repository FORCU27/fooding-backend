package im.fooding.app.dto.request.user.plan;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.plan.Plan.VisitStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
public class UserPlanRetrieveRequest extends BasicSearch {

    @Schema(description = "방문 상태 (SCHEDULED, COMPLETED, NOT_VISITED)", requiredMode = RequiredMode.REQUIRED, example = "SCHEDULED")
    VisitStatus visitStatus;
}
