package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreHour extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "store_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreDayType storeDayType;

    @Column(name = "open_at")
    private LocalTime openAt;

    @Column(name = "close_at")
    private LocalTime closeAt;

    @Column(name = "last_order_at")
    private LocalTime lastOrderAt;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    @Builder
    public StoreHour(
            Store store,
            StoreDayType storeDayType,
            LocalTime openAt,
            LocalTime closeAt,
            LocalTime lastOrderAt,
            boolean isClosed
    ) {
        this.store = store;
        this.storeDayType = storeDayType;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.lastOrderAt = lastOrderAt;
        this.isClosed = isClosed;
    }

    public void updateOpenAt(LocalTime openAt) {
        this.openAt = openAt;
    }

    public void updateCloseAt(LocalTime closeAt) {
        this.closeAt = closeAt;
    }

    public void updateLastOrderAt(LocalTime lastOrderAt) {
        this.lastOrderAt = lastOrderAt;
    }

    public void updateIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
}
