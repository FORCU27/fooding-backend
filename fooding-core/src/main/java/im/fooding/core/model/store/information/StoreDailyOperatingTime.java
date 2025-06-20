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
public class StoreDailyOperatingTime extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_operating_hour_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StoreOperatingHour storeOperatingHour;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private DayOfWeek dayOfWeek; // MONDAY ~ SUNDAY

    private LocalTime openTime;

    private LocalTime closeTime;

    private LocalTime breakStartTime;

    private LocalTime breakEndTime;

    @Builder
    public StoreDailyOperatingTime(StoreOperatingHour storeOperatingHour, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.storeOperatingHour = storeOperatingHour;
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public void update(DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, LocalTime breakStartTime, LocalTime breakEndTime) {
        this.dayOfWeek = dayOfWeek;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }
}
