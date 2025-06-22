package im.fooding.app.dto.request.user.device;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetrieveDeviceRequest extends BasicSearch {
    @Schema( description = "가게 ID", example="1")
    private Long storeId;

    @Schema( description = "검색어", example = "TEST-DV00" )
    private String searchString;

    @Schema( description = "디바이스 소유자 ID", example = "1" )
    private Long userId;
}
