package im.fooding.core.service.plan;

import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.plan.Plan.ReservationType;
import im.fooding.core.model.plan.Plan.VisitStatus;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    @Transactional
    public void create(StoreWaiting storeWaiting) {
        ReservationType reservationType = switch (storeWaiting.getChannel()) {
            case ONLINE -> ReservationType.ONLINE_WAITING;
            case IN_PERSON -> ReservationType.ONSITE_WAITING;
        };

        VisitStatus visitStatus = switch (storeWaiting.getStatus()) {
            case WAITING -> VisitStatus.SCHEDULED;
            case CANCELLED -> VisitStatus.NOT_VISITED;
            case SEATED -> VisitStatus.COMPLETED;
        };

        Plan plan = Plan.builder()
                .reservationType(reservationType)
                .userId(storeWaiting.getUser().getId())
                .originId(storeWaiting.getId())
                .storeId(storeWaiting.getStoreId())
                .visitStatus(visitStatus)
                .reservationTime(storeWaiting.getCreatedAt())
                .infantChairCount(storeWaiting.getInfantChairCount())
                .infantCount(storeWaiting.getInfantCount())
                .adultCount(storeWaiting.getAdultCount())
                .build();

        planRepository.save(plan);
    }

    @Transactional(readOnly = true)
    public Page<Plan> list(Long userId, Pageable pageable) {
        return planRepository.findAllByUserIdAndDeletedFalse(userId, pageable);
    }
}
