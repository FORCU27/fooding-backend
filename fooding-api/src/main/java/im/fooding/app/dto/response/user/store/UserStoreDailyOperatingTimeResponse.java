package im.fooding.app.dto.response.user.store;

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
public class UserStoreDailyOperatingTimeResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "요일 MONDAY, TUESDAY", example = "MONDAY", requiredMode = RequiredMode.REQUIRED)
    private DayOfWeek dayOfWeek;

    @Schema(description = "오픈시간", example = "10:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime openTime;

    @Schema(description = "마감시간", example = "22:00", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalTime closeTime;

    @Builder
    private UserStoreDailyOperatingTimeResponse(long id, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public static UserStoreDailyOperatingTimeResponse of(StoreDailyOperatingTime storeDailyOperatingTime) {
        return UserStoreDailyOperatingTimeResponse.builder()
                .id(storeDailyOperatingTime.getId())
                .dayOfWeek(storeDailyOperatingTime.getDayOfWeek())
                .openTime(storeDailyOperatingTime.getOpenTime())
                .closeTime(storeDailyOperatingTime.getCloseTime())
                .build();
    }
}
