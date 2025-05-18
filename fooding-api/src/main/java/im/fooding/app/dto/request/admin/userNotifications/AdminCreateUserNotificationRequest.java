package im.fooding.app.dto.request.admin.userNotifications;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.notification.NotificationCategory;
import im.fooding.core.model.notification.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminCreateUserNotificationRequest {
    @NotNull(message = "유저 ID를 입력해주세요.")
    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Size(max = 50, message = "제목은 50자 이내로 입력해주세요.")
    @Schema(description = "알림 제목", example = "긴급 시스템 점검 안내")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(description = "알림 내용", example = "오후 1시 시스템 점검 예정입니다.")
    private String content;
}