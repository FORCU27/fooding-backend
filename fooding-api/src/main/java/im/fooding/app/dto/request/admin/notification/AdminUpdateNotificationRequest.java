package im.fooding.app.dto.request.admin.notification;

import im.fooding.core.model.notification.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminUpdateNotificationRequest {
    @NotNull(message = "알림 번호를 입력해주세요.")
    @Schema(description = "알림 ID", example = "1")
    private Long id;

    @Size(max = 50, message = "제목은 50자 이내로 입력해주세요.")
    @Schema(description = "알림 제목", example = "긴급 시스템 점검 안내")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(description = "알림 내용", example = "오후 1시 시스템 점검 예정입니다.")
    private String content;

    @NotNull(message = "채널을 선택해주세요.")
    @Schema(description = "알림 채널", example = "MAIL")
    private NotificationChannel channel;

    @Schema(description = "예약 발송 시간", example = "2025-04-11 13:00:00")
    private LocalDateTime scheduledAt;
}
