package im.fooding.app.dto.response.user.reward;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
