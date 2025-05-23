package im.fooding.app.dto.response.admin.user;

import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminUserResponse {
    @Schema(description = "유저 ID", example = "1")
    private Long id;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "닉네임", example = "홍길동")
    private String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "가입일시", example = "2024-03-20 14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "마지막 로그인 일시", example = "2024-03-21 09:15:00")
    private LocalDateTime lastLoggedInAt;


    @Builder
    private AdminUserResponse(Long id, String email, String nickname, String phoneNumber,
            LocalDateTime createdAt, LocalDateTime lastLoggedInAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.lastLoggedInAt = lastLoggedInAt;
    }

    public static AdminUserResponse from(User user) {
        return AdminUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .lastLoggedInAt(user.getLastLoggedInAt())
                .build();
    }
}