package im.fooding.app.dto.request.admin.reward;

import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateRewardLogRequest {
    @NotNull(message = "가게 ID는 필수입니다")
    private Long storeId;

    @NotNull(message = "전화번호는 필수입니다")
    private String phoneNumber;

    @NotNull(message = "포인트는 필수입니다")
    @Positive(message = "포인트는 양수여야 합니다")
    private Integer point;

    @NotNull(message = "상태는 필수입니다")
    private RewardStatus status;

    @NotNull(message = "타입은 필수입니다")
    private RewardType type;

    @NotNull(message = "채널은 필수입니다")
    private RewardChannel channel;

    private String memo;
}
