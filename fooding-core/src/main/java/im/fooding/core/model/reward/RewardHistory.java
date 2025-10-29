package im.fooding.core.model.reward;

import im.fooding.core.model.store.Store;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RewardHistory {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name="phone_number", nullable=false )
    private String phoneNumber;

    @JoinColumn( name="store_id", nullable = true )
    @ManyToOne( fetch = FetchType.LAZY )
    private Store store;

    @Column( name="is_coupon", nullable=false )
    private boolean isCoupon;

    @Column( name="target_id", nullable=false )     // UserCoupon ID or RewardLog ID
    private long targetId;

    @Column( name="is_using", nullable=false )      // 사용하는 로그 or 적립하는 로그
    private boolean isUsing;

    @Enumerated( EnumType.STRING )
    @Column( nullable=false )
    private RewardHistoryStatus status;

    @Column( name="created_at", nullable=false )
    private LocalDateTime createdAt;

    private String memo;

    @Builder
    public RewardHistory(
            String phoneNumber,
            Store store,
            boolean isCoupon,
            long targetId,
            boolean isUsing,
            RewardHistoryStatus status,
            String memo
    ) {
        this.phoneNumber = phoneNumber;
        this.store = store;
        this.isCoupon = isCoupon;
        this.targetId = targetId;
        this.isUsing = isUsing;
        this.status = status;
        this.memo = memo;
    }

    public void updateMemo( String memo ) { this.memo = memo; }

}
