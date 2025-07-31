package im.fooding.app.service.user.plan;

import im.fooding.app.dto.request.user.plan.UserPlanRetrieveRequest;
import im.fooding.app.dto.response.user.plan.UserPlanResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.plan.Plan;
import im.fooding.core.service.plan.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPlanService {

    private final PlanService planService;

    public PageResponse<UserPlanResponse> list(UserPlanRetrieveRequest request, Long userId) {
        Page<Plan> plans = planService.list(userId, request.getPageable());
        Page<UserPlanResponse> planResponses = plans.map(UserPlanResponse::from);

        return PageResponse.of(planResponses.toList(), PageInfo.of(planResponses));
    }
}
