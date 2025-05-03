package im.fooding.app.dto.request.admin.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminIssueCouponRequest {
    @NotNull(message = "user id는 필수입니다.")
    @Schema(description = "user id", example = "1")
    private Long userId;

    @NotNull(message = "쿠폰 id는 필수입니다.")
    @Schema(description = "coupon id", example = "1")
    private Long couponId;

    public AdminIssueCouponRequest(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }
}
