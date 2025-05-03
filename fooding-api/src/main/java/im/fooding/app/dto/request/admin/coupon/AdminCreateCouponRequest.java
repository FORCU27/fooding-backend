package im.fooding.app.dto.request.admin.coupon;

import im.fooding.core.model.coupon.*;
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
public class AdminCreateCouponRequest {
    @Schema(description = "store id", example = "1")
    private Long storeId;

    @NotNull(message = "혜택 타입은 필수입니다.")
    @Schema(description = "혜택 타입", example = "DISCOUNT")
    private BenefitType benefitType;

    @NotNull(message = "쿠폰 타입은 필수입니다.")
    @Schema(description = "쿠폰 타입", example = "FIRST_COME_FIRST_SERVED")
    private CouponType type;

    @NotNull(message = "할인 타입은 필수입니다.")
    @Schema(description = "할인 타입", example = "FIXED")
    private DiscountType discountType;

    @NotNull(message = "제공 대상은 필수입니다.")
    @Schema(description = "제공 대상", example = "ALL")
    private ProvideType provideType;

    @NotBlank(message = "쿠폰명은 필수입니다.")
    @Size(max = 50, message = "쿠폰명은 최대 50자입니다.")
    @Schema(description = "쿠폰명", example = "2000원 할인쿠폰")
    private String name;

    @Size(max = 200, message = "사용조건은 최대 200자 입니다.")
    @Schema(description = "사용 조건", example = "메뉴 2개 이상 시킬시 사용 가능합니다.")
    private String conditions;

    @Schema(description = "총 발급수량", example = "50")
    private Integer totalQuantity;

    @Positive(message = "할인값은 필수입니다.")
    @Schema(description = "할인값(금액, 퍼센트)", example = "2000")
    private int discountValue;

    @NotNull(message = "발급시작일은 필수입니다.")
    @Schema(description = "발급시작일", example = "2025-05-01")
    private LocalDate issueStartOn;

    @Schema(description = "발급마감일", example = "2025-05-31")
    private LocalDate issueEndOn;

    @Schema(description = "사용기한", example = "2025-12-31")
    private LocalDate expiredOn;

    @NotNull(message = "상태값은 필수입니다.")
    @Schema(description = "상태", example = "ACTIVE")
    private CouponStatus status;

    public AdminCreateCouponRequest(Long storeId, BenefitType benefitType, CouponType type, DiscountType discountType, ProvideType provideType, String name, String conditions, Integer totalQuantity, int discountValue, LocalDate issueStartOn, LocalDate issueEndOn, LocalDate expiredOn, CouponStatus status) {
        this.storeId = storeId;
        this.benefitType = benefitType;
        this.type = type;
        this.discountType = discountType;
        this.provideType = provideType;
        this.name = name;
        this.conditions = conditions;
        this.totalQuantity = totalQuantity;
        this.discountValue = discountValue;
        this.issueStartOn = issueStartOn;
        this.issueEndOn = issueEndOn;
        this.expiredOn = expiredOn;
        this.status = status;
    }
}
