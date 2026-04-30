package im.fooding.core.model.store.information;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreDailyBreakTime extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_operating_hour_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StoreOperatingHour storeOperatingHour;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private DayOfWeek dayOfWeek; // MONDAY ~ SUNDAY

    private LocalTime breakStartTime;

    private LocalTime breakEndTime;

    @Builder
    public StoreDailyBreakTime(StoreOperatingHour storeOperatingHour, DayOfWeek dayOfWeek, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.storeOperatingHour = storeOperatingHour;
        this.dayOfWeek = dayOfWeek;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public void update(DayOfWeek dayOfWeek, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.dayOfWeek = dayOfWeek;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public boolean isBreakNow(LocalTime now) {
        // 시간 없으면 브레이크타임 없음
        if (breakStartTime == null || breakEndTime == null) {
            return false;
        }

        // 브레이크타임 여부
        if (breakEndTime.isAfter(breakStartTime)) {
            // 일반 브레이크 (같은 날)
            return !now.isBefore(breakStartTime) && now.isBefore(breakEndTime);
        } else {
            // 자정을 넘어가는 브레이크 (예: 23:00 ~ 01:00)
            return !now.isBefore(breakStartTime) || now.isBefore(breakEndTime);
        }
    }
}
