package im.fooding.app.dto.response.admin.banner;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import im.fooding.core.model.banner.Banner;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminBannerResponse {

    @Schema(description = "ID", requiredMode = REQUIRED, example = "6889bf12db34d469470f868e")
    String id;

    @Schema(description = "이름", requiredMode = REQUIRED, example = "흑백요리사 출연 셰프 맛집 예약")
    String name;

    @Schema(description = "설명", requiredMode = REQUIRED, example = "흑백요리사 출연 셰프 맛집 예약을 바로할 수 있는 링크로 연결되는 배너")
    String description;

    @Schema(description = "활성화 상태", requiredMode = REQUIRED, example = "true")
    Boolean isActive;

    @Schema(description = "우선 순위", requiredMode = REQUIRED, example = "0")
    Integer priority;

    @Schema(description = "링크", requiredMode = NOT_REQUIRED, example = "https://fooding.im/")
    String link;

    @Schema(description = "링크 종류", requiredMode = REQUIRED, example = "INTERNAL")
    Banner.LinkType linkType;

    public static AdminBannerResponse from(Banner banner) {
        return AdminBannerResponse.builder()
                .id(banner.getId().toString())
                .name(banner.getName())
                .description(banner.getDescription())
                .isActive(banner.isActive())
                .priority(banner.getPriority())
                .link(banner.getLink())
                .linkType(banner.getLinkType())
                .build();
    }
}
