package im.fooding.app.dto.request.user.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUseCouponRequest {
    @NotBlank
    @Schema(description = "테이블 번호", example = "1")
    private String tableNumber;
}
