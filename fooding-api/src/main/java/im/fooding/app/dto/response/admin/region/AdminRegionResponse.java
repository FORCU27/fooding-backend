package im.fooding.app.dto.response.admin.region;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import im.fooding.core.model.region.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminRegionResponse {

    @Schema(description = "지역 ID", requiredMode = REQUIRED, example = "KR-11")
    String id;

    @Schema(description = "부모 지역 ID", requiredMode = NOT_REQUIRED, example = "KR-11")
    String parentRegionId;

    @Schema(description = "지역 이름", requiredMode = REQUIRED, example = "서울특별시")
    String name;

    @Schema(description = "지역 시간대", requiredMode = REQUIRED, example = "Asia/Seoul")
    String timezone;

    @Schema(description = "국가 코드", requiredMode = REQUIRED, example = "KR")
    String countryCode;

    @Schema(description = "법적 코드", requiredMode = REQUIRED, example = "1100000000")
    String legalCode;

    @Schema(description = "지역 통화", requiredMode = REQUIRED, example = "KRW")
    String currency;

    @Schema(description = "지역 계층", requiredMode = REQUIRED, example = "1")
    Integer level;

    public static AdminRegionResponse from(Region region) {
        String parentRegionId = region.getParentRegion() != null ?
                region.getParentRegion().getId()
                : null;

        return AdminRegionResponse.builder()
                .id(region.getId())
                .parentRegionId(parentRegionId)
                .name(region.getName())
                .timezone(region.getTimezone())
                .countryCode(region.getCountryCode())
                .legalCode(region.getLegalCode())
                .currency(region.getCurrency())
                .level(region.getLevel())
                .build();
    }
}
