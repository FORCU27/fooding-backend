package im.fooding.realtime.app.domain.waiting;

import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.realtime.app.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("store_waiting")
@Getter
public class StoreWaiting extends BaseEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("store_id")
    private Long storeId;

    @Column("waiting_user_id")
    private Long waitingUserId;

    @Column("user_id")
    private Long user;

    @Column("call_number")
    private Integer callNumber;

    @Column("call_count")
    private Integer callCount;

    @Column("store_waiting_status")
    private StoreWaitingStatus status;

    @Column("channel")
    private StoreWaitingChannel channel;

    @Column("infant_chair_count")
    private Integer infantChairCount;

    @Column("infant_count")
    private Integer infantCount;

    @Column("adult_count")
    private Integer adultCount;

    @Column("memo")
    private String memo = "";

    @Builder
    public StoreWaiting(
            Long id,
            Long storeId,
            Long waitingUserId,
            Long user,
            Integer callNumber,
            Integer callCount,
            StoreWaitingStatus status,
            StoreWaitingChannel channel,
            Integer infantChairCount,
            Integer infantCount,
            Integer adultCount,
            String memo
    ) {
        this.id = id;
        this.storeId = storeId;
        this.waitingUserId = waitingUserId;
        this.user = user;
        this.callNumber = callNumber;
        this.callCount = callCount;
        this.status = status;
        this.channel = channel;
        this.infantChairCount = infantChairCount;
        this.infantCount = infantCount;
        this.adultCount = adultCount;
        this.memo = memo;
    }
}
