package im.fooding.app.dto.request.user.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIssueCouponRequest {
    @NotNull
    @Schema(description = "coupon id", example = "1")
    private Long couponId;
}
