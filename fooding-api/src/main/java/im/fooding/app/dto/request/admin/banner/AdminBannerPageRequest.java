package im.fooding.app.dto.request.admin.banner;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBannerPageRequest extends BasicSearch {

    @Schema(description = "활성화 여부", example = "true")
    private Boolean active;

    @Schema(description = "서비스", example = "HOME")
    private String service;

    @Schema(description = "노출 위치", example = "HEADER")
    private String placement;
}
