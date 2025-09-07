package im.fooding.core.repository.plan;

import im.fooding.core.model.plan.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QPlanRepository {

    Page<Plan> list(Pageable pageable);

    Page<Plan> list(PlanFilter filter, Pageable pageable);
}
