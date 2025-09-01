package im.fooding.app.dto.request.admin.banner;

import im.fooding.core.model.banner.Banner;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AdminBannerUpdateRequest {

    @Schema(description = "이름", example = "흑백요리사 출연 셰프 맛집 예약")
    @NotBlank
    String name;

    @Schema(description = "설명", example = "흑백요리사 출연 셰프 맛집 예약을 바로할 수 있는 링크로 연결되는 배너")
    @NotNull
    String description;

    @Schema(description = "활성화 상태", example = "true")
    @NotNull
    Boolean isActive;

    @Schema(description = "우선 순위", example = "0")
    @NotNull
    Integer priority;

    @Schema(description = "링크", example = "https://fooding.im/")
    String link;

    @Schema(description = "링크 종류", example = "INTERNAL")
    @NotNull
    Banner.LinkType linkType;
}
