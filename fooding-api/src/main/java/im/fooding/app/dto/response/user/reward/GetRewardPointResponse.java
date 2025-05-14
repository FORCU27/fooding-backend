package im.fooding.app.dto.response.user.reward;

import im.fooding.core.model.reward.RewardPoint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetRewardPointResponse {
    @Schema( description = "Reward Point ID" )
    private Long id;

    @Schema( description = "가게 이름" )
    private String storeName;

    @Schema( description = "전화번호" )
    private String phoneNumber;

    @Schema( description = "User ID" )
    private Long userId;

    @Schema( description = "누적 포인트" )
    private int point;

    @Builder
    public GetRewardPointResponse( Long id, String storeName, String phoneNumber, Long userId, int point){
        this.id = id;
        this.storeName = storeName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.point = point;
    }

    public static GetRewardPointResponse of(RewardPoint rewardPoint){
        return GetRewardPointResponse.builder()
                .id(rewardPoint.getId())
                .storeName( rewardPoint.getStore().getName())
                .phoneNumber( rewardPoint.getPhoneNumber())
                .userId( rewardPoint.getUser().getId())
                .point( rewardPoint.getPoint())
                .build();
    }
}
