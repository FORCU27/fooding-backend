package im.fooding.app.dto.request.admin.coupon;

import im.fooding.core.model.coupon.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AdminUpdateCouponRequest {
    @Schema(description = "store id", requiredMode = RequiredMode.NOT_REQUIRED, example = "1")
    private Long storeId;

    @NotNull
    @Schema(description = "혜택 타입", requiredMode = RequiredMode.REQUIRED, example = "DISCOUNT")
    private BenefitType benefitType;

    @NotNull
    @Schema(description = "쿠폰 타입", requiredMode = RequiredMode.REQUIRED, example = "FIRST_COME_FIRST_SERVED")
    private CouponType type;

    @NotNull
    @Schema(description = "할인 타입", requiredMode = RequiredMode.REQUIRED, example = "FIXED")
    private DiscountType discountType;

    @NotNull
    @Schema(description = "제공 대상", requiredMode = RequiredMode.REQUIRED, example = "ALL")
    private ProvideType provideType;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "쿠폰명", requiredMode = RequiredMode.REQUIRED, example = "2000원 할인쿠폰")
    private String name;

    @Size(max = 200)
    @Schema(description = "사용 조건", requiredMode = RequiredMode.NOT_REQUIRED, example = "메뉴 2개 이상 시킬시 사용 가능합니다.")
    private String conditions;

    @Schema(description = "총 발급수량", requiredMode = RequiredMode.NOT_REQUIRED, example = "50")
    private Integer totalQuantity;

    @Positive
    @Schema(description = "할인값(금액, 퍼센트)", requiredMode = RequiredMode.REQUIRED, example = "2000")
    private int discountValue;

    @NotNull
    @Schema(description = "발급시작일", requiredMode = RequiredMode.REQUIRED, example = "2025-05-01")
    private LocalDate issueStartOn;

    @Schema(description = "발급마감일", requiredMode = RequiredMode.NOT_REQUIRED, example = "2025-05-31")
    private LocalDate issueEndOn;

    @Schema(description = "사용기한", requiredMode = RequiredMode.NOT_REQUIRED, example = "2025-12-31")
    private LocalDate expiredOn;

    @NotNull
    @Schema(description = "상태", requiredMode = RequiredMode.REQUIRED, example = "ACTIVE")
    private CouponStatus status;

    @Builder
    public AdminUpdateCouponRequest(Long storeId, BenefitType benefitType, CouponType type, DiscountType discountType, ProvideType provideType, String name, String conditions, Integer totalQuantity, int discountValue, LocalDate issueStartOn, LocalDate issueEndOn, LocalDate expiredOn, CouponStatus status) {
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
