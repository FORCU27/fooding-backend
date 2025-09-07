package im.fooding.core.repository.plan;

import im.fooding.core.model.plan.Plan.VisitStatus;
import lombok.Builder;

@Builder
public record PlanFilter(
        Long userId,
        VisitStatus visitStatus
) {

    public static PlanFilter non() {
        return new PlanFilter(null, null);
    }
}
