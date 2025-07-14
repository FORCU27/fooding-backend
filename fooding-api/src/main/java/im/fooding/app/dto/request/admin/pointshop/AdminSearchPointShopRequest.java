package im.fooding.app.dto.request.admin.pointshop;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchPointShopRequest extends BasicSearch {
    @Schema(description = "가게 id", example = "1")
    private Long storeId;

    @NotNull
    @Schema(description = "판매여부", example = "true")
    private Boolean isActive;
}
