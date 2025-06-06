package im.fooding.core.model.waiting;

import im.fooding.core.model.BaseEntity;
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
    @JoinColumn(name = "waiting_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Waiting waiting;

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

    @Builder
    private WaitingSetting(
            Waiting waiting,
            String label,
            int minimumCapacity,
            int maximumCapacity,
            Integer estimatedWaitingTimeMinutes,
            boolean isActive,
            int entryTimeLimitMinutes
    ) {
        this.waiting = waiting;
        this.label = label;
        this.minimumCapacity = minimumCapacity;
        this.maximumCapacity = maximumCapacity;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.isActive = isActive;
        this.entryTimeLimitMinutes = entryTimeLimitMinutes;
    }

    public void update(
            Waiting waiting,
            String label,
            int minimumCapacity,
            int maximumCapacity,
            Integer estimatedWaitingTimeMinutes,
            boolean isActive,
            int entryTimeLimitMinutes
    ) {
        this.waiting = waiting;
        this.label = label;
        this.minimumCapacity = minimumCapacity;
        this.maximumCapacity = maximumCapacity;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.isActive = isActive;
        this.entryTimeLimitMinutes = entryTimeLimitMinutes;
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
}
