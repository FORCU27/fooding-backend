package im.fooding.app.dto.request.ceo.coupon;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.coupon.CouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CeoSearchCouponRequest extends BasicSearch {
    @NotNull
    @Schema(description = "store id", example = "1")
    private Long storeId;

    @Schema(description = "쿠폰 상태 ACTIVE, INACTIVE", example = "ACTIVE")
    private CouponStatus status;
}
