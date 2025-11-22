package im.fooding.core.service.plan;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.plan.Plan.ReservationType;
import im.fooding.core.model.plan.Plan.VisitStatus;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.repository.plan.PlanFilter;
import im.fooding.core.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final PlanRepository planRepository;

    @Transactional
    public ObjectId create(StoreWaiting storeWaiting) {
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

        return planRepository.save(plan).getId();
    }

    @Transactional
    public void cancelByStoreWaiting(long id) {
        Plan plan = getByOriginId(id);

        plan.cancel();
        planRepository.save(plan);
    }

    public Page<Plan> list(PlanFilter filter, Pageable pageable) {
        return planRepository.list(filter, pageable);
    }

    public Plan getPlan(ObjectId id) {
        return planRepository.findById(id)
                .filter(plan -> !plan.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.PLAN_NOT_FOUND));
    }

    private Plan getByOriginId(long id) {
        return planRepository.findByOriginId(id)
                .filter(plan -> !plan.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.PLAN_NOT_FOUND));
    }

    public Plan findByUserIdAndStoreId( long userId, long storeId ){
        return planRepository.findByUserIdAndStoreIdAndDeletedFalse( userId, storeId );
    }
}
