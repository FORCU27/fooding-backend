package im.fooding.core.model.waiting;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "waitings")
public class Waiting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "store_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            unique = true
    )
    private Store store;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WaitingStatus status;

    public Waiting(Store store, WaitingStatus status) {
        this.store = store;
        this.status = status;
    }

    public void updateStatus(WaitingStatus status) {
        this.status = status;
    }
}
