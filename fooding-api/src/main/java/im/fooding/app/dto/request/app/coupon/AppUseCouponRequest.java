package im.fooding.app.dto.request.app.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppUseCouponRequest {
    @NotNull
    @Schema(description = "쿠폰 ID")
    private Long id;
}
