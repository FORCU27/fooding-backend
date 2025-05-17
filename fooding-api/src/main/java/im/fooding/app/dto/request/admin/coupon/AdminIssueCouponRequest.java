package im.fooding.app.dto.request.admin.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminIssueCouponRequest {
    @NotNull
    @Schema(description = "user id", requiredMode = RequiredMode.REQUIRED, example = "1")
    private Long userId;

    @NotNull
    @Schema(description = "coupon id", requiredMode = RequiredMode.REQUIRED, example = "1")
    private Long couponId;

    public AdminIssueCouponRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }
}
