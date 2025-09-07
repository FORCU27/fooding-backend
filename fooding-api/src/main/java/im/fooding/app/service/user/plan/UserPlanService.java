package im.fooding.app.service.user.plan;

import im.fooding.app.dto.request.user.plan.UserPlanRetrieveRequest;
import im.fooding.app.dto.response.user.plan.UserPlanResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.plan.Plan;
import im.fooding.core.repository.plan.PlanFilter;
import im.fooding.core.service.plan.PlanService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPlanService {

    private final PlanService planService;

    public PageResponse<UserPlanResponse> list(UserPlanRetrieveRequest request, Long userId) {
        PlanFilter filter = PlanFilter.builder()
                .userId(userId)
                .visitStatus(request.getVisitStatus())
                .build();
        Page<Plan> plans = planService.list(filter, request.getPageable());
        Page<UserPlanResponse> planResponses = plans.map(UserPlanResponse::from);

        return PageResponse.of(planResponses.toList(), PageInfo.of(planResponses));
    }

    public UserPlanResponse getPlan(String id) {
        return UserPlanResponse.from(planService.getPlan(new ObjectId(id)));
    }
}
