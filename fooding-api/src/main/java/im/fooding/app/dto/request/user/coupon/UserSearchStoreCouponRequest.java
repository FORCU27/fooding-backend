package im.fooding.app.dto.request.user.coupon;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchStoreCouponRequest extends BasicSearch {
    @NotNull
    @Schema(description = "가게 id", example = "1")
    private Long storeId;
}
