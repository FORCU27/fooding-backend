package im.fooding.app.dto.request.admin.banner;

import im.fooding.core.model.banner.Banner;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.Map;

@Value
public class AdminBannerCreateRequest {

    @Schema(description = "이름", example = "흑백요리사 출연 셰프 맛집 예약")
    @NotBlank
    String name;

    @Schema(description = "설명", example = "흑백요리사 출연 셰프 맛집 예약을 바로할 수 있는 링크로 연결되는 배너")
    @NotNull
    String description;

    @Schema(description = "활성화 상태", example = "true")
    @NotNull
    Boolean active;

    @Schema(description = "우선 순위", example = "0")
    @NotNull
    Integer priority;

    @Schema(description = "링크", example = "https://fooding.im/")
    String link;

    @Schema(description = "링크 종류", example = "INTERNAL")
    @NotNull
    Banner.LinkType linkType;

    @Schema(description = "이미지 업로드 ID", example = "e4b7f1a2-...", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String imageId;

    @Schema(description = "서비스", example = "HOME", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String service;

    @Schema(description = "노출 위치", example = "HEADER", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    String placement;

    @Schema(description = "추가 파라미터(JSON)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    Map<String, Object> parameters;
}
