package im.fooding.app.dto.response.ceo.store;

import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class CeoStoreDailyOperatingTimeResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "요일 MONDAY, TUESDAY", example = "MONDAY", requiredMode = RequiredMode.REQUIRED)
    private DayOfWeek dayOfWeek;

    @Schema(description = "오픈시간", example = "10:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime openTime;

    @Schema(description = "마감시간", example = "22:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime closeTime;

    @Schema(description = "브레이크타임 시작", example = "13:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime breakStartTime;

    @Schema(description = "브레이크타임 끝", example = "15:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime breakEndTime;

    @Builder
    private CeoStoreDailyOperatingTimeResponse(long id, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public static CeoStoreDailyOperatingTimeResponse of(StoreDailyOperatingTime storeDailyOperatingTime) {
        return CeoStoreDailyOperatingTimeResponse.builder()
                .id(storeDailyOperatingTime.getId())
                .dayOfWeek(storeDailyOperatingTime.getDayOfWeek())
                .openTime(storeDailyOperatingTime.getOpenTime())
                .closeTime(storeDailyOperatingTime.getCloseTime())
                .breakStartTime(storeDailyOperatingTime.getBreakStartTime())
                .breakEndTime(storeDailyOperatingTime.getBreakEndTime())
                .build();
    }
}
