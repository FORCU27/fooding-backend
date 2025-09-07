package im.fooding.app.dto.request.ceo.store.information;

import im.fooding.core.model.store.information.StoreRegularHolidayType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@NoArgsConstructor
public class CeoCreateStoreOperatingHourRequest {
    @NotNull
    @Schema(description = "휴무 여부", example = "true")
    private Boolean hasHoliday;

    @Schema(description = "정기 휴무 타입 WEEKLY, MONTHLY", example = "WEEKLY")
    private StoreRegularHolidayType regularHolidayType;

    @Schema(description = "요일 MONDAY, TUESDAY", example = "MONDAY")
    private DayOfWeek regularHoliday;

    @Schema(description = "공휴일 중 휴무일", example = "[\"새해\",\"설날연휴\",\"추석연휴\"]")
    private List<String> closedNationalHolidays;

    @Schema(description = "기타 휴무일", example = "[\"2025-08-21\", \"2025-12-24\"]")
    private List<String> customHolidays;

    @Schema(description = "운영시간 관련 비고/메모", example = "연중무휴")
    private String operatingNotes;
    
    @Schema(description = "영업시간")
    @Valid
    private List<CeoCreateStoreDailyOperatingTimeRequest> dailyOperatingTimes;

    @Builder
    public CeoCreateStoreOperatingHourRequest(Boolean hasHoliday, StoreRegularHolidayType regularHolidayType, DayOfWeek regularHoliday, List<String> closedNationalHolidays, List<String> customHolidays, String operatingNotes, List<CeoCreateStoreDailyOperatingTimeRequest> dailyOperatingTimes) {
        this.hasHoliday = hasHoliday;
        this.regularHolidayType = regularHolidayType;
        this.regularHoliday = regularHoliday;
        this.closedNationalHolidays = closedNationalHolidays;
        this.customHolidays = customHolidays;
        this.operatingNotes = operatingNotes;
        this.dailyOperatingTimes = dailyOperatingTimes;
    }
}
