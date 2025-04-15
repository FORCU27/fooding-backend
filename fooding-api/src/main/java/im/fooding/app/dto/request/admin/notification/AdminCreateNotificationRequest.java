package im.fooding.app.dto.request.admin.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.notification.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdminCreateNotificationRequest {
    @NotBlank(message = "발신자를 입력해주세요.")
    @Schema(description = "알림 발신자", example = "system@example.com")
    private String source;

    @NotEmpty(message = "수신자를 한 명 이상 입력해주세요.")
    @Schema(description = "알림 수신자 목록", example = "[\"user123\", \"user@example.com\"]")
    private List<String> destinations;

    @Size(max = 50, message = "제목은 50자 이내로 입력해주세요.")
    @Schema(description = "알림 제목", example = "긴급 시스템 점검 안내")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(description = "알림 내용", example = "오후 1시 시스템 점검 예정입니다.")
    private String content;

    @NotNull(message = "채널을 선택해주세요.")
    @Schema(description = "알림 채널", example = "MAIL")
    private NotificationChannel channel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "예약 발송 시간은 미래여야 합니다.")
    @Schema(description = "예약 발송 시간(옵션)", example = "2025-04-11 13:00:00")
    private LocalDateTime scheduledAt;
}
