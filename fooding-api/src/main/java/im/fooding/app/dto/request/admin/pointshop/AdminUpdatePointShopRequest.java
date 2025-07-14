package im.fooding.app.dto.request.admin.pointshop;

import im.fooding.core.model.coupon.ProvideType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdminUpdatePointShopRequest {
    @NotBlank
    @Schema(description = "상품이름", example = "계란찜")
    private String name;

    @PositiveOrZero
    @Schema(description = "100", example = "포인트")
    private int point;

    @NotNull
    @Schema(description = "ALL(모든고객), REGULAR_CUSTOMER(단골전용)", example = "ALL")
    private ProvideType provideType;

    @Schema(description = "가게 id", example = "가게에서 사용 가능")
    private String conditions;

    @Schema(description = "가게 id", example = "100")
    private Integer totalQuantity;

    @Schema(description = "교환 가능 시작일", example = "2025-07-01")
    private LocalDate issueStartOn;

    @Schema(description = "교환 가능 마감일", example = "2030-07-01")
    private LocalDate issueEndOn;
}
