package im.fooding.app.dto.request.user.device;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RetrieveDeviceRequest extends BasicSearch {
    @Schema( description = "가게 ID", example="1")
    private Long storeId;
}
