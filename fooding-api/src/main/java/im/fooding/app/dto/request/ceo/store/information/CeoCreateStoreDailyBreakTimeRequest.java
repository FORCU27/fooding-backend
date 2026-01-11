package im.fooding.app.dto.request.ceo.store.information;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class CeoCreateStoreDailyBreakTimeRequest {
    @NotNull
    @Schema(description = "요일 MONDAY, TUESDAY ... SUNDAY", example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @Schema(description = "브레이크타임 시작", example = "13:00")
    private LocalTime breakStartTime;

    @Schema(description = "브레이크타임 끝", example = "15:00")
    private LocalTime breakEndTime;

    @Builder
    public CeoCreateStoreDailyBreakTimeRequest(DayOfWeek dayOfWeek, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.dayOfWeek = dayOfWeek;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }
}
