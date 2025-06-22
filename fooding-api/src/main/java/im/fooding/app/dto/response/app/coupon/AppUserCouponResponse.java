package im.fooding.app.dto.response.app.coupon;

import im.fooding.core.model.coupon.BenefitType;
import im.fooding.core.model.coupon.DiscountType;
import im.fooding.core.model.coupon.UserCoupon;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AppUserCouponResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "user id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "coupon id", example = "1", requiredMode = RequiredMode.NOT_REQUIRED)
    private Long couponId;

    @Schema(description = "store id", example = "1", requiredMode = RequiredMode.NOT_REQUIRED)
    private Long storeId;

    @Schema(description = "유저 닉네임", example = "김개명", requiredMode = RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "store 명", example = "김가네", requiredMode = RequiredMode.NOT_REQUIRED)
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

    @Schema(description = "사용여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private boolean used;

    @Schema(description = "사용일자", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime usedAt;

    @Schema(description = "만료일자", example = "2025-03-16", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDate expiredOn;

    @Schema(description = "발급일자", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdDateAt;

    @Builder
    private AppUserCouponResponse(Long id, Long userId, Long couponId, Long storeId, String nickname, String storeName, String name, String conditions, BenefitType benefitType, DiscountType discountType, int discountValue, boolean used, LocalDateTime usedAt, LocalDate expiredOn, LocalDateTime createdDateAt) {
        this.id = id;
        this.userId = userId;
        this.couponId = couponId;
        this.storeId = storeId;
        this.nickname = nickname;
        this.storeName = storeName;
        this.name = name;
        this.conditions = conditions;
        this.benefitType = benefitType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.used = used;
        this.usedAt = usedAt;
        this.expiredOn = expiredOn;
        this.createdDateAt = createdDateAt;
    }

    public static AppUserCouponResponse of(UserCoupon userCoupon) {
        return AppUserCouponResponse.builder()
                .id(userCoupon.getId())
                .userId(userCoupon.getUser().getId())
                .couponId(null != userCoupon.getCoupon() ? userCoupon.getCoupon().getId() : null)
                .storeId(null != userCoupon.getStore() ? userCoupon.getStore().getId() : null)
                .nickname(userCoupon.getUser().getNickname())
                .storeName(null != userCoupon.getStore() ? userCoupon.getStore().getName() : null)
                .name(userCoupon.getName())
                .conditions(userCoupon.getConditions())
                .benefitType(userCoupon.getBenefitType())
                .discountType(userCoupon.getDiscountType())
                .discountValue(userCoupon.getDiscountValue())
                .used(userCoupon.isUsed())
                .usedAt(userCoupon.getUsedAt())
                .expiredOn(userCoupon.getExpiredOn())
                .createdDateAt(userCoupon.getCreatedAt())
                .build();
    }
}
