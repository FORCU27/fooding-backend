package im.fooding.app.dto.request.user.region;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class UserRegionListRequest extends BasicSearch {

    @Schema(description = "지역 레벨 (필터링 용)", example = "1")
    Integer level;

    @Schema(description = "부모 지역 ID", example = "KR-11")
    String parentRegionId;
}
