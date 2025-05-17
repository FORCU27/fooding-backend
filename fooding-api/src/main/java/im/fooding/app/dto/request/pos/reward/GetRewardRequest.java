package im.fooding.app.dto.request.pos.reward;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRewardRequest extends BasicSearch {
        @NotNull
        @Schema( description = "가게 ID" )
        Long storeId;

        @Schema( description = "고객 전화번호" )
        String phoneNumber;

        @Schema( description = "리워드 상태" )
        RewardStatus status;

        @Schema( description = "리워드 채널" )
        RewardChannel channel;

        @Schema( description = "리워드 타입" )
        RewardType type;

        @Schema( description = "검색어" )
        String searchString;
}
