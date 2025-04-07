package im.fooding.core.model.waiting;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

// todo: Store 객체 추가
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreWaiting extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiting_user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private WaitingUser user;

    @Column(name = "call_number", nullable = false)
    private int callNumber;

    @Column(name = "channel", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreWaitingChannel channel;

    @Column(name = "infant_chair_count", nullable = false)
    private int infantChairCount;

    @Column(name = "infant_count", nullable = false)
    private int infantCount;

    @Column(name = "adult_count", nullable = false)
    private int adultCount;

    @Column(name = "memo", nullable = false)
    private String memo;

//    @Builder
//    public StoreWaiting(WaitingUser user, Store store, int callNumber, WaitingChannel channel, int infantChairCount, int infantCount, int adultCount) {
//        this.user = user;
//        this.store = sotre;
//        this.callNumber = callNumber;
//        this.channel = channel;
//        this.infantChairCount = infantChairCount;
//        this.infantCount = infantCount;
//        this.adultCount = adultCount;
//        this.memo = "";
//    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
