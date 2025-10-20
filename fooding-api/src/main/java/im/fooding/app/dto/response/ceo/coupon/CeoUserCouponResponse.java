package im.fooding.app.dto.response.ceo.coupon;

import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.coupon.UserCouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CeoUserCouponResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "user id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "프로필 이미지 URL", requiredMode = RequiredMode.NOT_REQUIRED)
    private String profileImage;

    @Schema(description = "유저 닉네임", example = "김개명", requiredMode = RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "쿠폰 상태 (AVAILABLE, REQUESTED, USED)", example = "AVAILABLE", requiredMode = RequiredMode.REQUIRED)
    private UserCouponStatus status;

    @Schema(description = "사용일자", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDateTime usedAt;

    @Schema(description = "발급일자", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Builder
    private CeoUserCouponResponse(Long id, Long userId, String profileImage, String nickname, UserCouponStatus status, LocalDateTime usedAt, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.status = status;
        this.usedAt = usedAt;
        this.createdAt = createdAt;
    }

    public static CeoUserCouponResponse of(UserCoupon userCoupon) {
        return CeoUserCouponResponse.builder()
                .id(userCoupon.getId())
                .userId(userCoupon.getUser().getId())
                .profileImage(userCoupon.getUser().getProfileImage())
                .nickname(userCoupon.getUser().getNickname())
                .status(userCoupon.getStatus())
                .usedAt(userCoupon.getUsedAt())
                .createdAt(userCoupon.getCreatedAt())
                .build();
    }
}
