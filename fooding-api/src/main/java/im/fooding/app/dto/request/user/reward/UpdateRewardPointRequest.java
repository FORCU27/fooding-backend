package im.fooding.app.dto.request.user.reward;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
public class UpdateRewardPointRequest {
    @NotNull
    @Pattern(regexp = "^\\d{11}$")
    @Schema( description = "전화번호", requiredMode = Schema.RequiredMode.REQUIRED )
    private String phoneNumber;

    @NotNull
    @Schema( description = "가게 ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long storeId;

    @NotNull
    @Schema( description = "포인트", requiredMode = Schema.RequiredMode.REQUIRED )
    private int point;

    @NotNull
    @Schema( description = "리워드 타입", requiredMode = Schema.RequiredMode.REQUIRED )
    private RewardType type;

    @NotNull
    @Schema( description = "리워드 채널", requiredMode = Schema.RequiredMode.REQUIRED )
    private RewardChannel channel;
}
