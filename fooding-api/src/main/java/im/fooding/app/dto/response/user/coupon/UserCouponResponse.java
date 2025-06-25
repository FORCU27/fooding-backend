package im.fooding.app.dto.response.user.coupon;

import im.fooding.core.model.coupon.BenefitType;
import im.fooding.core.model.coupon.DiscountType;
import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.coupon.UserCouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserCouponResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "store id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long storeId;

    @Schema(description = "store 이름", example = "김가네", requiredMode = RequiredMode.NOT_REQUIRED)
    private String storeName;

    @Schema(description = "쿠폰명", example = "2000원 할인쿠폰", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "사용 조건", example = "메뉴 2개 이상 시킬시 사용 가능합니다.", requiredMode = RequiredMode.NOT_REQUIRED)
    private String conditions;

    @Schema(description = "혜택 타입", example = "DISCOUNT", requiredMode = RequiredMode.REQUIRED)
    private BenefitType benefitType;

    @Schema(description = "할인 타입", example = "FIXED", requiredMode = RequiredMode.REQUIRED)
    private DiscountType discountType;

    @Schema(description = "할인값(금액, 퍼센트)", example = "2000", requiredMode = RequiredMode.REQUIRED)
    private int discountValue;

    @Schema(description = "쿠폰 상태 (AVAILABLE, REQUESTED, USED)", example = "AVAILABLE", requiredMode = RequiredMode.REQUIRED)
    private UserCouponStatus status;

    @Schema(description = "사용일자", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime usedAt;

    @Schema(description = "만료일자", example = "2025-03-16", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDate expiredOn;

    @Schema(description = "발급일자", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdDateAt;

    @Builder
    private UserCouponResponse(Long id, Long storeId, String storeName, String name, String conditions, BenefitType benefitType, DiscountType discountType, int discountValue, UserCouponStatus status, LocalDateTime usedAt, LocalDate expiredOn, LocalDateTime createdDateAt) {
        this.id = id;
        this.storeId = storeId;
        this.storeName = storeName;
        this.name = name;
        this.conditions = conditions;
        this.benefitType = benefitType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.status = status;
        this.usedAt = usedAt;
        this.expiredOn = expiredOn;
        this.createdDateAt = createdDateAt;
    }

    public static UserCouponResponse of(UserCoupon userCoupon) {
        return UserCouponResponse.builder()
                .id(userCoupon.getId())
                .storeId(null != userCoupon.getStore() ? userCoupon.getStore().getId() : null)
                .storeName(null != userCoupon.getStore() ? userCoupon.getStore().getName() : null)
                .name(userCoupon.getName())
                .conditions(userCoupon.getConditions())
                .benefitType(userCoupon.getBenefitType())
                .discountType(userCoupon.getDiscountType())
                .discountValue(userCoupon.getDiscountValue())
                .status(userCoupon.getStatus())
                .usedAt(userCoupon.getUsedAt())
                .expiredOn(userCoupon.getExpiredOn())
                .createdDateAt(userCoupon.getCreatedAt())
                .build();
    }
}
