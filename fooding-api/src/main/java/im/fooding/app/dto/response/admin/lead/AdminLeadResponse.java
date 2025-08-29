package im.fooding.app.dto.response.admin.lead;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminLeadResponse {

    @Schema(description = "ID", example = "1")
    Long id;

    @Schema(description = "이름", example = "홍길동")
    String name;

    @Schema(description = "연락처", example = "010-0000-0000")
    String phone;

    @Schema(description = "유입 경로", example = "NAVER_ADS")
    String source;

    @Schema(description = "생성일", example = "2025-08-29T12:00:00Z")
    String createdAt;
}

