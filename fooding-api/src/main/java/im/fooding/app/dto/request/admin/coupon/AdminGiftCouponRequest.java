package im.fooding.app.dto.request.admin.coupon;

import im.fooding.core.model.coupon.BenefitType;
import im.fooding.core.model.coupon.DiscountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdminGiftCouponRequest {
    @NotNull(message = "유저 id는 필수입니다.")
    @Schema(description = "user id", example = "1")
    private Long userId;

    @Schema(description = "store id", example = "1")
    private Long storeId;

    @NotNull(message = "혜택 타입은 필수입니다.")
    @Schema(description = "혜택 타입", example = "DISCOUNT")
    private BenefitType benefitType;

    @NotNull(message = "할인 타입은 필수입니다.")
    @Schema(description = "할인 타입", example = "FIXED")
    private DiscountType discountType;

    @NotBlank(message = "쿠폰명은 필수입니다.")
    @Size(max = 50, message = "쿠폰명은 최대 50자입니다.")
    @Schema(description = "쿠폰명", example = "2000원 할인쿠폰")
    private String name;

    @Size(max = 200, message = "사용 조건은 최대 200자입니다.")
    @Schema(description = "사용 조건", example = "메뉴 2개 이상 시킬시 사용 가능합니다.")
    private String conditions;

    @Positive(message = "할인값은 필수입니다.")
    @Schema(description = "할인값(금액, 퍼센트)", example = "2000")
    private int discountValue;

    @Schema(description = "사용기한", example = "2025-12-31")
    private LocalDate expiredOn;

    public AdminGiftCouponRequest(Long userId, Long storeId, BenefitType benefitType, DiscountType discountType, String name, String conditions, int discountValue, LocalDate expiredOn) {
        this.userId = userId;
        this.storeId = storeId;
        this.benefitType = benefitType;
        this.discountType = discountType;
        this.name = name;
        this.conditions = conditions;
        this.discountValue = discountValue;
        this.expiredOn = expiredOn;
    }
}
