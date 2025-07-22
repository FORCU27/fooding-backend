package im.fooding.core.model.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
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
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreWaiting extends BaseEntity {

    private static final Set<StoreWaitingStatus> REVERSIBLE_STATUSES = Set.of(
            StoreWaitingStatus.CANCELLED,
            StoreWaitingStatus.SEATED
    );

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiting_user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private WaitingUser waitingUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "call_number", nullable = false)
    private int callNumber;

    @Column(name = "call_count", nullable = false)
    private int callCount;

    @Column(name = "store_waiting_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreWaitingStatus status;

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
    private String memo = "";

    @Builder
    public StoreWaiting(
            WaitingUser waitingUser,
            Store store,
            int callNumber,
            StoreWaitingStatus status,
            StoreWaitingChannel channel,
            int infantChairCount,
            int infantCount,
            int adultCount,
            String memo
    ) {
        this.waitingUser = waitingUser;
        this.store = store;
        this.callNumber = callNumber;
        this.callCount = 0;
        this.status = status;
        this.channel = channel;
        this.infantChairCount = infantChairCount;
        this.infantCount = infantCount;
        this.adultCount = adultCount;
        this.memo = memo;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void updateOccupancy(int adultCount, int infantCount, int infantChairCount) {
        this.adultCount = adultCount;
        this.infantCount = infantCount;
        this.infantChairCount = infantChairCount;
    }

    public void seat() {
        if (status != StoreWaitingStatus.WAITING) {
            throw new ApiException(ErrorCode.STORE_WAITING_ILLEGAL_STATE_SEAT);
        }

        this.status = StoreWaitingStatus.SEATED;
    }

    public void cancel() {
        if (status != StoreWaitingStatus.WAITING) {
            throw new ApiException(ErrorCode.STORE_WAITING_ILLEGAL_STATE_CANCEL);
        }

        this.status = StoreWaitingStatus.CANCELLED;
    }

    public void revert() {
        if (!REVERSIBLE_STATUSES.contains(status)) {
            throw new ApiException(ErrorCode.STORE_WAITING_ILLEGAL_STATE_REVERT);
        }

        status = StoreWaitingStatus.WAITING;
    }

    public String getChannelValue() {
        return channel.getValue();
    }

    public Long getStoreId() {
        return store.getId();
    }

    public String getStoreName() {
        return store.getName();
    }

    public void call() {
        callCount++;
    }

    public void injectUser(WaitingUser waitingUser) {
        this.waitingUser = waitingUser;
    }

    public void update(
            WaitingUser waitingUser,
            Store store,
            StoreWaitingStatus status,
            StoreWaitingChannel channel,
            Integer infantChairCount,
            Integer infantCount,
            Integer adultCount,
            String memo
    ) {
        this.waitingUser = waitingUser;
        this.store = store;
        this.status = status;
        this.channel = channel;
        this.infantChairCount = infantChairCount;
        this.infantCount = infantCount;
        this.adultCount = adultCount;
        this.memo = memo;
    }
}
