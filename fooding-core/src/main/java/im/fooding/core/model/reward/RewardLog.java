package im.fooding.core.model.reward;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
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
@Table( name = "reward_log" )
public class RewardLog extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) )
    private Store store;

    @Column( name = "phone_number" )
    private String phoneNumber;

    @Column( name = "point" )
    private int point;

    @Column( name =  "status" )
    @Enumerated( EnumType.STRING )
    private RewardStatus status;

    @Column( name = "type" )
    @Enumerated( EnumType.STRING )
    private RewardType type;

    @Column( name = "channel" )
    @Enumerated( EnumType.STRING )
    private RewardChannel channel;

    @Builder
    public RewardLog(
            Store store,
            String phoneNumber,
            int point,
            RewardStatus status,
            RewardType type,
            RewardChannel channel
    ){
        this.store = store;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.status = status;
        this.type = type;
        this.channel = channel;
    }

    public void updateStatus( RewardStatus status ){
        this.status = status;
    }
}
