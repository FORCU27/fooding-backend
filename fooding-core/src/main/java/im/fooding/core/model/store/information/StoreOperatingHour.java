package im.fooding.core.model.store.information;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Filter;

import java.time.DayOfWeek;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreOperatingHour extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    private boolean hasHoliday;

    @Enumerated(EnumType.STRING)
    private StoreRegularHolidayType regularHolidayType;

    @Enumerated(EnumType.STRING)
    private DayOfWeek regularHoliday;

    private String closedNationalHolidays; // 공휴일 중 휴무일 "새해,설날연휴,추석연휴,크리스마스.."

    private String customHolidays; // "2025-08-21,2025-12-25,2026-01-01"

    private String operatingNotes;

    @OneToMany(mappedBy = "storeOperatingHour")
    @BatchSize(size = 7)
    private List<StoreDailyOperatingTime> dailyOperatingTimes;

    @OneToMany(mappedBy = "storeOperatingHour")
    @BatchSize(size = 7)
    private List<StoreDailyBreakTime> dailyBreakTimes;

    @Builder
    public StoreOperatingHour(Store store, boolean hasHoliday, StoreRegularHolidayType regularHolidayType, DayOfWeek regularHoliday, String closedNationalHolidays, String customHolidays, String operatingNotes) {
        this.store = store;
        this.hasHoliday = hasHoliday;
        this.regularHolidayType = regularHolidayType;
        this.regularHoliday = regularHoliday;
        this.closedNationalHolidays = closedNationalHolidays;
        this.customHolidays = customHolidays;
        this.operatingNotes = operatingNotes;
    }

    public void update(boolean hasHoliday, StoreRegularHolidayType regularHolidayType, DayOfWeek regularHoliday, String closedNationalHolidays, String customHolidays, String operatingNotes) {
        this.hasHoliday = hasHoliday;
        this.regularHolidayType = regularHolidayType;
        this.regularHoliday = regularHoliday;
        this.closedNationalHolidays = closedNationalHolidays;
        this.customHolidays = customHolidays;
        this.operatingNotes = operatingNotes;
    }
}
