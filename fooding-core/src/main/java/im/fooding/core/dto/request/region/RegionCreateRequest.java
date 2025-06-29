package im.fooding.core.dto.request.region;

import im.fooding.core.model.region.Region;
import lombok.Builder;

@Builder
public record RegionCreateRequest(
        String id,
        Region parentRegion,
        String name,
        String timezone,
        String countryCode,
        String legalCode,
        String currency,
        Integer level
) {

    public Region toRegion() {
        return Region.builder()
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
