package im.fooding.app.dto.request.app.coupon;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppSearchCouponRequest extends BasicSearch {
    @NotNull
    @Schema(description = "가게 ID")
    private Long storeId;

    @NotBlank
    @Schema(description = "전화번호")
    private String phoneNumber;

    @Schema(description = "사용여부")
    private Boolean used;
}
