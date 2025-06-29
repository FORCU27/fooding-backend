package im.fooding.app.dto.request.admin.region;

import im.fooding.core.dto.request.region.RegionCreateRequest;
import im.fooding.core.model.region.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AdminRegionCreateRequest {

    @Schema(description = "지역 ID", example = "KR-11")
    @NotBlank
    String id;

    @Schema(description = "부모 지역 ID", example = "KR-11")
    String parentRegionId;

    @Schema(description = "지역 이름", example = "서울특별시")
    @NotBlank
    String name;

    @Schema(description = "지역 시간대", example = "Asia/Seoul")
    @NotBlank
    String timezone;

    @Schema(description = "국가 코드", example = "KR")
    @NotBlank
    String countryCode;

    @Schema(description = "법적 코드", example = "1100000000")
    @NotBlank
    String legalCode;

    @Schema(description = "지역 통화", example = "KRW")
    @NotBlank
    String currency;

    @Schema(description = "지역 계층", example = "1")
    @NotNull
    Integer level;

    public RegionCreateRequest toRegionCreateRequest(Region parentRegion) {
        return RegionCreateRequest.builder()
                .id(id)
                .parentRegion(parentRegion)
                .name(name)
                .timezone(timezone)
                .countryCode(countryCode)
                .legalCode(legalCode)
                .currency(currency)
                .level(level)
                .build();
    }
}
