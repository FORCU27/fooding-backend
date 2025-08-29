package im.fooding.app.dto.request.admin.reward;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateRewardPointRequest {
    @NotNull(message = "가게 ID는 필수입니다")
    private Long storeId;

    @NotNull(message = "전화번호는 필수입니다")
    private String phoneNumber;

    private Long userId;

    @NotNull(message = "포인트는 필수입니다")
    @Positive(message = "포인트는 양수여야 합니다")
    private Integer point;

    private String memo;
}
