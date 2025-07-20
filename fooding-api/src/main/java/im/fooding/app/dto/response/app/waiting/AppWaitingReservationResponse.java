package im.fooding.app.dto.response.app.waiting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class AppWaitingReservationResponse {

    @Schema(description = "ID", requiredMode = RequiredMode.REQUIRED, example = "ID")
    long id;

    @Schema(description = "예약 type (RESERVATION, ON_SITE_WAITING, ONLINE_WAITING)", requiredMode = RequiredMode.REQUIRED, example = "RESERVATION")
    ReservationType type;

    @Schema(description = "가게 이름", requiredMode = RequiredMode.REQUIRED, example = "바다풍경 정육식당 흑돼지 용담탑동본점")
    String storeName;

    @Schema(description = "방문 상태 (SCHEDULED, COMPLETED, NOT_VISITED)", requiredMode = RequiredMode.REQUIRED, example = "SCHEDULED")
    VisitStatus visitStatus;

    @Schema(description = "예약 일시 (예약시 존재)", requiredMode = RequiredMode.NOT_REQUIRED, example = "2025-04-30T15:30:00")
    LocalDateTime reservationTime;

    @Schema(description = "예약 인원 수", requiredMode = RequiredMode.REQUIRED, example = "3")
    int numberOfPeople;

    @RequiredArgsConstructor
    public enum VisitStatus {
        SCHEDULED,      // 방문예정
        COMPLETED,      // 방문완료
        NOT_VISITED,    // 취소/노쇼
        ;
    }

    @RequiredArgsConstructor
    public enum ReservationType {
        RESERVATION,        // 예약
        ON_SITE_WAITING,    // 현장 웨이팅
        ONLINE_WAITING,     // 온라인 웨이팅
        ;
    }

    public static AppWaitingReservationResponse from(StoreWaiting storeWaiting) {
        ReservationType type = storeWaiting.getChannel() == StoreWaitingChannel.ONLINE
                ? ReservationType.ONLINE_WAITING
                : ReservationType.ON_SITE_WAITING;
        VisitStatus visitStatus = toVisitStatus(storeWaiting.getStatus());
        int numberOfPeople = storeWaiting.getAdultCount() + storeWaiting.getInfantCount();

        return AppWaitingReservationResponse.builder()
                .id(storeWaiting.getId())
                .type(type)
                .storeName(storeWaiting.getStoreName())
                .visitStatus(visitStatus)
                .numberOfPeople(numberOfPeople)
                .build();
    }

    private static VisitStatus toVisitStatus(StoreWaitingStatus storeWaitingStatus) {
        return switch (storeWaitingStatus) {
            case WAITING ->  VisitStatus.SCHEDULED;
            case SEATED -> VisitStatus.COMPLETED;
            case CANCELLED -> VisitStatus.NOT_VISITED;
        };
    }
}
