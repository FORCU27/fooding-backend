package im.fooding.core.model.waiting;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.StoreService;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "waiting_settings")
public class WaitingSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_service_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StoreService storeService;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private int minimumCapacity;

    @Column(nullable = false)
    private int maximumCapacity;

    @Column(nullable = false)
    private int estimatedWaitingTimeMinutes;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private int entryTimeLimitMinutes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WaitingStatus status;

    @Builder
    private WaitingSetting(
            StoreService storeService,
            String label,
            int minimumCapacity,
            int maximumCapacity,
            Integer estimatedWaitingTimeMinutes,
            boolean isActive,
            int entryTimeLimitMinutes,
            WaitingStatus status
    ) {
        this.storeService = storeService;
        this.label = label;
        this.minimumCapacity = minimumCapacity;
        this.maximumCapacity = maximumCapacity;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.isActive = isActive;
        this.entryTimeLimitMinutes = entryTimeLimitMinutes;
        this.status = status;
    }

    public void update(
            StoreService storeService,
            String label,
            int minimumCapacity,
            int maximumCapacity,
            Integer estimatedWaitingTimeMinutes,
            boolean isActive,
            int entryTimeLimitMinutes,
            WaitingStatus status
    ) {
        this.storeService = storeService;
        this.label = label;
        this.minimumCapacity = minimumCapacity;
        this.maximumCapacity = maximumCapacity;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.isActive = isActive;
        this.entryTimeLimitMinutes = entryTimeLimitMinutes;
        this.status = status;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void updateWaitingTimeMinutes(int estimatedWaitingTimeMinutes) {
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
    }

    public void updateStatus(WaitingStatus status) {
        this.status = status;
    }

    public boolean isOpen() {
        return status == WaitingStatus.WAITING_OPEN;
    }
}
