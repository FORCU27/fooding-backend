package im.fooding.core.repository.banner;

import lombok.Builder;

@Builder
public record BannerFilter(
        Boolean active
) {

    public static BannerFilter non() {
        return new BannerFilter(null);
    }
}
