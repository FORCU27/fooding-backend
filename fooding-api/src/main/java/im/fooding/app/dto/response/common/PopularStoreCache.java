package im.fooding.app.dto.response.common;

import im.fooding.core.dto.response.StoreImageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import java.util.List;
import lombok.Builder;

@Builder
public record PopularStoreCache(
        Long id,
        StoreCategory category,
        String regionId,
        String name,
        String address,
        int visitCount,
        int reviewCount,
        int bookmarkCount,
        double averageRating,
        List<StoreImageResponse> images
) {

    public static PopularStoreCache from(Store store) {
        return PopularStoreCache.builder()
                .id(store.getId())
                .category(store.getCategory())
                .regionId(store.getRegionId())
                .name(store.getName())
                .address(store.getAddress())
                .visitCount(store.getVisitCount())
                .reviewCount(store.getReviewCount())
                .bookmarkCount(store.getBookmarkCount())
                .averageRating(store.getAverageRating())
                .images(store.getImages())
                .build();

    }
}
