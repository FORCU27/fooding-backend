package im.fooding.app.dto.response.admin.coupon;

import im.fooding.core.model.coupon.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminCouponResponse {
    @Schema(description = "id", example = "1")
    private long id;

    @Schema(description = "store id", example = "1")
    private Long storeId;

    @Schema(description = "store 이름", example = "김가네")
    private String storeName;

    @Schema(description = "혜택 타입", example = "DISCOUNT")
    private BenefitType benefitType;

    @Schema(description = "쿠폰 타입", example = "FIRST_COME_FIRST_SERVED")
    private CouponType type;

    @Schema(description = "할인 타입", example = "FIXED")
    private DiscountType discountType;

    @Schema(description = "제공 대상", example = "ALL")
    private ProvideType provideType;

    @Schema(description = "쿠폰명", example = "2000원 할인쿠폰")
    private String name;

    @Schema(description = "사용 조건", example = "메뉴 2개 이상 시킬시 사용 가능합니다.")
    private String conditions;

    @Schema(description = "총 발급수량", example = "50")
    private Integer totalQuantity;

    @Schema(description = "할인값(금액, 퍼센트)", example = "2000")
    private int discountValue;

    @Schema(description = "발급시작일", example = "2025-05-01")
    private LocalDate issueStartOn;

    @Schema(description = "발급마감일", example = "2025-05-31")
    private LocalDate issueEndOn;

    @Schema(description = "사용기한", example = "2025-12-31")
    private LocalDate expiredOn;

    @Schema(description = "상태", example = "ACTIVE")
    private CouponStatus status;

    @Schema(description = "상태", example = "2025-03-16T05:17:04.069")
    private LocalDateTime createdAt;

    @Schema(description = "상태", example = "2025-03-16T05:17:04.069")
    private LocalDateTime updatedAt;

    @Builder
    private AdminCouponResponse(long id, Long storeId, String storeName, BenefitType benefitType, CouponType type, DiscountType discountType, ProvideType provideType, String name, String conditions, Integer totalQuantity, int discountValue, LocalDate issueStartOn, LocalDate issueEndOn, LocalDate expiredOn, CouponStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.storeId = storeId;
        this.storeName = storeName;
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AdminCouponResponse of(Coupon coupon) {
        return AdminCouponResponse.builder()
                .id(coupon.getId())
                .storeId(null != coupon.getStore() ? coupon.getStore().getId() : null)
                .storeName(null != coupon.getStore() ? coupon.getStore().getName() : null)
                .benefitType(coupon.getBenefitType())
                .type(coupon.getType())
                .discountType(coupon.getDiscountType())
                .provideType(coupon.getProvideType())
                .name(coupon.getName())
                .conditions(coupon.getConditions())
                .totalQuantity(coupon.getTotalQuantity())
                .discountValue(coupon.getDiscountValue())
                .issueStartOn(coupon.getIssueStartOn())
                .issueEndOn(coupon.getIssueEndOn())
                .expiredOn(coupon.getExpiredOn())
                .status(coupon.getStatus())
                .createdAt(coupon.getCreatedAt())
                .updatedAt(coupon.getUpdatedAt())
                .build();
    }
}
