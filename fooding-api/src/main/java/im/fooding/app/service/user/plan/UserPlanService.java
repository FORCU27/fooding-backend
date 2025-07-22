package im.fooding.app.service.user.plan;

import im.fooding.app.dto.request.user.plan.UserPlanRetrieveRequest;
import im.fooding.app.dto.response.user.plan.UserPlanResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.service.waiting.StoreWaitingService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPlanService {

    private final StoreWaitingService storeWaitingService;

    public PageResponse<UserPlanResponse> list(UserPlanRetrieveRequest request, Long userId) {
        Pageable pageable = request.getPageable();

        List<Object> merged = new ArrayList<>();

        Page<StoreWaiting> storeWaitings = storeWaitingService.listByUserId(userId, pageable);
        merged.addAll(storeWaitings.getContent());

        Page<UserPlanResponse> userPlanResponsePage = toUserPlanResponsePage(
                merged, pageable
        );

        return PageResponse.of(
                userPlanResponsePage.toList(),
                PageInfo.of(userPlanResponsePage)
        );
    }
    private Page<UserPlanResponse> toUserPlanResponsePage(
            List<Object> merged, Pageable pageable
    ) {
        List<UserPlanResponse> mappedList = merged.stream()
                .map(obj -> {
                    if (obj instanceof StoreWaiting sw) {
                        return UserPlanResponse.from(sw);
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        // 페이징 처리
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = Math.min(start + pageable.getPageSize(), mappedList.size());
        List<UserPlanResponse> pageContent =
                start > mappedList.size() ? Collections.emptyList() : mappedList.subList(start, end);
         return new PageImpl<>(pageContent, pageable, mappedList.size());
    }
}
