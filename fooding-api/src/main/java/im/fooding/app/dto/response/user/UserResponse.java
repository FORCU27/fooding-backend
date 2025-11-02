package im.fooding.app.dto.response.user;

import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "유저 응답")
public class UserResponse {

    @Schema(description = "유저 ID")
    private Long id;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "프로필 이미지 URL")
    private String profileImage;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "주소 상세")
    private String addressDetail;

    @Schema(description = "가입일")
    private LocalDateTime createdAt;

    @Schema(description = "마지막 수정일")
    private LocalDateTime updatedAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .address(user.getAddress())
                .addressDetail(user.getAddressDetail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
