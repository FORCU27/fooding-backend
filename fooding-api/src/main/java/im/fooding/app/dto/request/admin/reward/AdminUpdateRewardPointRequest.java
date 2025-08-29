package im.fooding.app.dto.request.admin.reward;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateRewardPointRequest {
    private Long storeId;
    private String phoneNumber;
    private Long userId;
    
    @Positive(message = "포인트는 양수여야 합니다")
    private Integer point;
    
    private String memo;
}
