package im.fooding.core.repository.banner;

import lombok.Builder;

@Builder
public record BannerFilter(
        Boolean active,
        String service,
        String placement,
        String searchString
) {

    public static BannerFilter non() {
        return BannerFilter.builder().build();
    }
}
