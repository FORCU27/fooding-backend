package im.fooding.app.dto.request.admin.region;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class AdminRegionListRequest extends BasicSearch {

    @Schema(description = "부모 지역 ID", example = "KR-11")
    String parentRegionId;
}
