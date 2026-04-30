package im.fooding.app.dto.response.ceo.store;

import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.store.information.StoreRegularHolidayType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@NoArgsConstructor
public class CeoStoreOperatingHourResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "휴무 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private Boolean hasHoliday;

    @Schema(description = "정기 휴무 타입 WEEKLY, MONTHLY", example = "WEEKLY", requiredMode = RequiredMode.NOT_REQUIRED)
    private StoreRegularHolidayType regularHolidayType;

    @Schema(description = "요일 MONDAY, TUESDAY", example = "MONDAY", requiredMode = RequiredMode.NOT_REQUIRED)
    private DayOfWeek regularHoliday;

    @Schema(description = "공휴일 중 휴무일", example = "[\"새해\",\"설날연휴\",\"추석연휴\",\"크리스마스\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<String> closedNationalHolidays;

    @Schema(description = "기타 휴무일", example = "[\"2025-08-21\", \"2025-12-24\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<String> customHolidays;

    @Schema(description = "운영시간 관련 비고/메모", example = "연중무휴", requiredMode = RequiredMode.NOT_REQUIRED)
    private String operatingNotes;

    @Schema(description = "운영시간", requiredMode = RequiredMode.REQUIRED)
    private List<CeoStoreDailyOperatingTimeResponse> dailyOperatingTimes;

    @Schema(description = "휴게시간", requiredMode = RequiredMode.REQUIRED)
    private List<CeoStoreDailyBreakTimeResponse> dailyBreakTimes;

    @Builder
    private CeoStoreOperatingHourResponse(long id, Boolean hasHoliday, StoreRegularHolidayType regularHolidayType, DayOfWeek regularHoliday,
                                          List<String> closedNationalHolidays, List<String> customHolidays, String operatingNotes,
                                          List<CeoStoreDailyOperatingTimeResponse> dailyOperatingTimes, List<CeoStoreDailyBreakTimeResponse> dailyBreakTimes) {
        this.id = id;
        this.hasHoliday = hasHoliday;
        this.regularHolidayType = regularHolidayType;
        this.regularHoliday = regularHoliday;
        this.closedNationalHolidays = closedNationalHolidays;
        this.customHolidays = customHolidays;
        this.operatingNotes = operatingNotes;
        this.dailyOperatingTimes = dailyOperatingTimes;
        this.dailyBreakTimes = dailyBreakTimes;
    }

    public static CeoStoreOperatingHourResponse of(StoreOperatingHour storeOperatingHour) {
        return CeoStoreOperatingHourResponse.builder()
                .id(storeOperatingHour.getId())
                .hasHoliday(storeOperatingHour.isHasHoliday())
                .regularHolidayType(storeOperatingHour.getRegularHolidayType())
                .regularHoliday(storeOperatingHour.getRegularHoliday())
                .closedNationalHolidays(Util.generateStringToList(storeOperatingHour.getClosedNationalHolidays()))
                .customHolidays(Util.generateStringToList(storeOperatingHour.getCustomHolidays()))
                .operatingNotes(storeOperatingHour.getOperatingNotes())
                .dailyOperatingTimes(storeOperatingHour.getDailyOperatingTimes().stream().map(CeoStoreDailyOperatingTimeResponse::of).toList())
                .dailyBreakTimes(storeOperatingHour.getDailyBreakTimes().stream().map(CeoStoreDailyBreakTimeResponse::of).toList())
                .build();
    }
}
