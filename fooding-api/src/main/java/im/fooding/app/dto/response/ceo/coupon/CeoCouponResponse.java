package im.fooding.app.dto.response.ceo.coupon;

import im.fooding.core.model.coupon.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CeoCouponResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "store id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long storeId;

    @Schema(description = "혜택 타입", example = "DISCOUNT", requiredMode = RequiredMode.REQUIRED)
    private BenefitType benefitType;

    @Schema(description = "쿠폰 타입", example = "FIRST_COME_FIRST_SERVED", requiredMode = RequiredMode.REQUIRED)
    private CouponType type;

    @Schema(description = "할인 타입", example = "FIXED", requiredMode = RequiredMode.REQUIRED)
    private DiscountType discountType;

    @Schema(description = "제공 대상", example = "ALL", requiredMode = RequiredMode.REQUIRED)
    private ProvideType provideType;

    @Schema(description = "쿠폰명", example = "2000원 할인쿠폰", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "사용 조건", example = "메뉴 2개 이상 시킬시 사용 가능합니다.", requiredMode = RequiredMode.NOT_REQUIRED)
    private String conditions;

    @Schema(description = "총 발급수량", example = "50", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer totalQuantity;

    @Schema(description = "발급된 수량", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int issuedQuantity;

    @Schema(description = "할인값(금액, 퍼센트)", example = "2000", requiredMode = RequiredMode.REQUIRED)
    private int discountValue;

    @Schema(description = "발급시작일", example = "2025-05-01", requiredMode = RequiredMode.REQUIRED)
    private LocalDate issueStartOn;

    @Schema(description = "발급마감일", example = "2025-05-31", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDate issueEndOn;

    @Schema(description = "사용기한", example = "2025-12-31", requiredMode = RequiredMode.REQUIRED)
    private LocalDate expiredOn;

    @Schema(description = "상태", example = "ACTIVE", requiredMode = RequiredMode.REQUIRED)
    private CouponStatus status;

    @Schema(description = "등록일", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Schema(description = "수정일", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime updatedAt;

    @Builder
    private CeoCouponResponse(long id, Long storeId, BenefitType benefitType, CouponType type, DiscountType discountType, ProvideType provideType, String name, String conditions, Integer totalQuantity, int issuedQuantity, int discountValue, LocalDate issueStartOn, LocalDate issueEndOn, LocalDate expiredOn, CouponStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.storeId = storeId;
        this.benefitType = benefitType;
        this.type = type;
        this.discountType = discountType;
        this.provideType = provideType;
        this.name = name;
        this.conditions = conditions;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = issuedQuantity;
        this.discountValue = discountValue;
        this.issueStartOn = issueStartOn;
        this.issueEndOn = issueEndOn;
        this.expiredOn = expiredOn;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CeoCouponResponse of(Coupon coupon) {
        return CeoCouponResponse.builder()
                .id(coupon.getId())
                .storeId(null != coupon.getStore() ? coupon.getStore().getId() : null)
                .benefitType(coupon.getBenefitType())
                .type(coupon.getType())
                .discountType(coupon.getDiscountType())
                .provideType(coupon.getProvideType())
                .name(coupon.getName())
                .conditions(coupon.getConditions())
                .totalQuantity(coupon.getTotalQuantity())
                .issuedQuantity(coupon.getIssuedQuantity())
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
