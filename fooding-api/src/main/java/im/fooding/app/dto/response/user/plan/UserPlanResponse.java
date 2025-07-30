package im.fooding.app.dto.response.user.plan;

import im.fooding.core.model.plan.Plan;
import im.fooding.core.model.plan.Plan.ReservationType;
import im.fooding.core.model.plan.Plan.VisitStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class UserPlanResponse {

    @Schema(description = "ID", requiredMode = RequiredMode.REQUIRED, example = "6889bf12db34d469470f868e")
    String id;

    @Schema(description = "예약 type (RESERVATION, ONSITE_WAITING, ONLINE_WAITING)", requiredMode = RequiredMode.REQUIRED, example = "RESERVATION")
    ReservationType reservationType;

    @Schema(description = "예약/웨이팅 도메인의 ID", requiredMode = RequiredMode.REQUIRED, example = "1")
    long originId;

    @Schema(description = "가게 이름", requiredMode = RequiredMode.REQUIRED, example = "바다풍경 정육식당 흑돼지 용담탑동본점")
    long storeId;

    @Schema(description = "방문 상태 (SCHEDULED, COMPLETED, NOT_VISITED)", requiredMode = RequiredMode.REQUIRED, example = "SCHEDULED")
    VisitStatus visitStatus;

    @Schema(description = "예약 일시", requiredMode = RequiredMode.REQUIRED, example = "2025-07-30T06:01:16.711Z")
    LocalDateTime reservationTime;

    @Schema(description = "예약한 유아용 의자 수", requiredMode = RequiredMode.REQUIRED, example = "3")
    int infantChairCount;

    @Schema(description = "예약한 유아 수", requiredMode = RequiredMode.REQUIRED, example = "3")
    int infantCount;

    @Schema(description = "예약한 성인 수", requiredMode = RequiredMode.REQUIRED, example = "3")
    int adultCount;

    public static UserPlanResponse from(Plan plan) {
        return new UserPlanResponse(
                plan.getId().toString(),
                plan.getReservationType(),
                plan.getOriginId(),
                plan.getStoreId(),
                plan.getVisitStatus(),
                plan.getReservationTime(),
                plan.getInfantChairCount(),
                plan.getInfantCount(),
                plan.getAdultCount()
        );
    }
}
