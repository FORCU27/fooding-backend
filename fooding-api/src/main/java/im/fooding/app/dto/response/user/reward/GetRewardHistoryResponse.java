package im.fooding.app.dto.response.user.reward;

import im.fooding.core.model.reward.RewardHistory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class GetRewardHistoryResponse {
    private Long id;
    private String phoneNumber;
    private Long storeId;
    private Long targetId;          // UserCouponId vs. RewardLogId
    private LocalDateTime createdAt;
    private String memo;

    public static GetRewardHistoryResponse of(
            RewardHistory history
    ){
        return GetRewardHistoryResponse.builder()
                .id(history.getId())
                .phoneNumber(history.getPhoneNumber())
                .storeId( history.getStore().getId() )
                .targetId(history.getTargetId())
                .createdAt(history.getCreatedAt())
                .memo(history.getMemo())
                .build();
    }
}
