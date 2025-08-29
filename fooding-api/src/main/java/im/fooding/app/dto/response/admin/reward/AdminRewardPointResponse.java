package im.fooding.app.dto.response.admin.reward;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminRewardPointResponse {
    private Long id;
    private Long storeId;
    private String storeName;
    private String phoneNumber;
    private Long userId;
    private String userName;
    private Integer point;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
