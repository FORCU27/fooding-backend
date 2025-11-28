package im.fooding.realtime.app.domain.reward;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.realtime.app.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table( "reward_point" )
@Getter
@Setter
@NoArgsConstructor
public class RewardPoint extends BaseEntity {
    @Id
    private Long id;

    @Column("store_id")
    private Long storeId;

    @Column("phone_number")
    private String phoneNumber;

    @Column("user_id")
    private Long userId;

    @Column("point")
    private int point;

    @Column("memo")
    private String memo;

    @Builder
    public RewardPoint(Long storeId, String phoneNumber, Long userId, int point, String memo) {
        this.storeId = storeId;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.point = point;
        this.memo = memo;
    }

    public void usePoint( int usePoint ){
        if( this.point < usePoint ) throw new ApiException(ErrorCode.REWARD_POINT_NOT_ENOUGH);
        this.point -= usePoint;
    }

    public void addPoint( int earnPoint ) { this.point += earnPoint; }
}
