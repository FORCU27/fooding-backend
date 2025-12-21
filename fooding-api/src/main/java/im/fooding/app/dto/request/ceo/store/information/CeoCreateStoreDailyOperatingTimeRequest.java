package im.fooding.app.dto.request.ceo.store.information;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class CeoCreateStoreDailyOperatingTimeRequest {
    @NotNull
    @Schema(description = "요일 MONDAY, TUESDAY ... SUNDAY", example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @Schema(description = "오픈시간", example = "10:00")
    private LocalTime openTime;

    @Schema(description = "마감시간", example = "22:00")
    private LocalTime closeTime;

    public CeoCreateStoreDailyOperatingTimeRequest(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}
