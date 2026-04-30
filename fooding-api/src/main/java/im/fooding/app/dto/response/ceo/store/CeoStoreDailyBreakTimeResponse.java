package im.fooding.app.dto.response.ceo.store;

import im.fooding.core.model.store.information.StoreDailyBreakTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class CeoStoreDailyBreakTimeResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "요일 MONDAY, TUESDAY", example = "MONDAY", requiredMode = RequiredMode.REQUIRED)
    private DayOfWeek dayOfWeek;

    @Schema(description = "브레이크타임 시작", example = "13:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime breakStartTime;

    @Schema(description = "브레이크타임 끝", example = "15:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime breakEndTime;

    @Builder
    private CeoStoreDailyBreakTimeResponse(long id, DayOfWeek dayOfWeek, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public static CeoStoreDailyBreakTimeResponse of(StoreDailyBreakTime storeDailyBreakTime) {
        return CeoStoreDailyBreakTimeResponse.builder()
                .id(storeDailyBreakTime.getId())
                .dayOfWeek(storeDailyBreakTime.getDayOfWeek())
                .breakStartTime(storeDailyBreakTime.getBreakStartTime())
                .breakEndTime(storeDailyBreakTime.getBreakEndTime())
                .build();
    }
}
