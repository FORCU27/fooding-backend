package im.fooding.core.dto.request.region;

import im.fooding.core.model.region.Region;
import lombok.Builder;

@Builder
public record RegionUpdateRequest(
        String id,
        Region parentRegion,
        String name,
        String timezone,
        String countryCode,
        String legalCode,
        String currency,
        Integer level
) {
}
