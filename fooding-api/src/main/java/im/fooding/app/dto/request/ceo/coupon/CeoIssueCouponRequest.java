package im.fooding.app.dto.request.ceo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoIssueCouponRequest {
    @NotNull
    @Schema(description = "user id", example = "1")
    private Long userId;

    @NotNull
    @Schema(description = "coupon id", example = "1")
    private Long couponId;

    public CeoIssueCouponRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }
}
