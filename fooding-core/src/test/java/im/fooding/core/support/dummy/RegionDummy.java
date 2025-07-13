package im.fooding.core.support.dummy;

import im.fooding.core.model.region.Region;

public class RegionDummy {

    public static Region create() {
        return Region.builder()
                .id("테스트")
                .parentRegion(null)
                .name("테스트")
                .timezone("테스트")
                .countryCode("테스트")
                .legalCode("테스트")
                .currency("테스트")
                .level(1)
                .build();
    }
}
