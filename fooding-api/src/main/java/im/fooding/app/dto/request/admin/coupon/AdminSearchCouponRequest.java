package im.fooding.app.dto.request.admin.coupon;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchCouponRequest extends BasicSearch {
    @Schema(description = "가게 id", requiredMode = RequiredMode.NOT_REQUIRED, example = "1")
    private Long storeId;
}
