package im.fooding.realtime.app.domain.reward;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table( name = "reward_log" )
public class RewardLog extends BaseEntity {
    @Id
    @Column( "id" )
    private Long id;

    @Column( "store_id" )
    private Long storeId;

    @Column( "phone_number" )
    private String phoneNumber;

    @Column( "point" )
    private int point;

    @Column( "status" )
    private RewardStatus status;

    @Column( "type" )
    private RewardType type;

    @Column( "channel" )
    private RewardChannel channel;

    @Column( "memo" )
    private String memo;

    @Builder
    public RewardLog(
            Long storeId,
            String phoneNumber,
            int point,
            RewardStatus status,
            RewardType type,
            RewardChannel channel,
            String memo
    ){
        this.storeId = storeId;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.status = status;
        this.type = type;
        this.channel = channel;
        this.memo = memo;
    }

    public void updateStatus( RewardStatus status ){this.status = status;}
    public void updateMemo( String memo ) {this.memo = memo;}
}
