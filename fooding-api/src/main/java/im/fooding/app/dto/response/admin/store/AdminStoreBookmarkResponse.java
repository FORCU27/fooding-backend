package im.fooding.app.dto.response.admin.store;

import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminStoreBookmarkResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "유저 id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "프로필 이미지", example = "https://...", requiredMode = RequiredMode.NOT_REQUIRED)
    private String profileImage;

    @Schema(description = "닉네임", example = "고독한 미식가", requiredMode = RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "인증", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int verifiedCount;

    @Schema(description = "등록일", example = "2025-04-25 12:00:00", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Builder
    private AdminStoreBookmarkResponse(Long id, Long userId, String profileImage, String nickname, int verifiedCount, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.verifiedCount = verifiedCount;
        this.createdAt = createdAt;
    }

    public static AdminStoreBookmarkResponse of(Bookmark bookmark) {
        User user = bookmark.getUser();
        return AdminStoreBookmarkResponse.builder()
                .id(bookmark.getId())
                .userId(user.getId())
                .profileImage(user.getProfileImage())
                .nickname(user.getNickname())
                .verifiedCount(bookmark.getVerifiedCount())
                .createdAt(bookmark.getCreatedAt())
                .build();
    }
}
