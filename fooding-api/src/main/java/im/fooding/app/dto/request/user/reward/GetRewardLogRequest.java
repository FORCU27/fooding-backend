package im.fooding.app.dto.request.user.reward;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.reward.RewardStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetRewardLogRequest extends BasicSearch {
    @Schema( description = "가게 ID" )
    private Long storeId;

    @Schema( description = "전화번호" )
    private String phoneNumber;

    @Schema( description = "검색어" )
    private String searchString;

    @Schema( description = "리워드 로그 형태" )
    private RewardStatus status;
}
