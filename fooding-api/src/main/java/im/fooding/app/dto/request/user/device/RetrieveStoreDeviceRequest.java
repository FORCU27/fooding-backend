package im.fooding.app.dto.request.user.device;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RetrieveStoreDeviceRequest extends BasicSearch {
    @NotNull
    @Schema( description = "가게 ID", example="1")
    private Long storeId;
}
