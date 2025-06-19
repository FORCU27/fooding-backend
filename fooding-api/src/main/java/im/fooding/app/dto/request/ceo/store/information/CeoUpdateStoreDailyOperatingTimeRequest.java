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
public class CeoUpdateStoreDailyOperatingTimeRequest {
    @NotNull
    @Schema(description = "id", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "요일 MONDAY, TUESDAY", example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @NotNull
    @Schema(description = "오픈시간", example = "10:00")
    private LocalTime openTime;

    @NotNull
    @Schema(description = "마감시간", example = "22:00")
    private LocalTime closeTime;

    @Schema(description = "브레이크타임 시작", example = "13:00")
    private LocalTime breakStartTime;

    @Schema(description = "브레이크타임 끝", example = "15:00")
    private LocalTime breakEndTime;

    @Builder
    public CeoUpdateStoreDailyOperatingTimeRequest(Long id, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }
}
