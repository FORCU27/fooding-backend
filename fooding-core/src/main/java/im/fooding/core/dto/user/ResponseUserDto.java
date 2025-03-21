package im.fooding.core.dto.user;

import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseUserDto {
    @Schema(description = "id", example = "1")
    private long id;

    @Schema(description = "이메일", example = "admin@gmail.com")
    private String email;

    @Schema(description = "닉네임", example = "관리자")
    private String nickname;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String mobile;

    @Schema(description = "등록일자", example = "2025-03-15T05:17:04.069")
    private LocalDateTime createdDateTime;

    @Schema(description = "수정일자", example = "2025-03-16T05:17:04.069")
    private LocalDateTime updatedDateTime;

    @Builder
    private ResponseUserDto(long id, String email, String nickname, String mobile, LocalDateTime createdDateTime, LocalDateTime updatedDateTime) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.mobile = mobile;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
    }

    public static ResponseUserDto of(User user) {
        return ResponseUserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .mobile(user.getMobile())
                .createdDateTime(user.getCreatedDateTime())
                .updatedDateTime(user.getUpdatedDateTime())
                .build();
    }
}
