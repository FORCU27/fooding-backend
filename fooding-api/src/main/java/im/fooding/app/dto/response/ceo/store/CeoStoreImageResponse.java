package im.fooding.app.dto.response.ceo.store;

import im.fooding.core.model.store.StoreImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoStoreImageResponse {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "이미지 url", example = "https://..")
    private String imageUrl;

    @Schema(description = "정렬순서", example = "1")
    private int sortOrder;

    @Schema(description = "태그", example = "업체,음식")
    private String tags;

    @Builder
    private CeoStoreImageResponse(Long id, String imageUrl, int sortOrder, String tags) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.tags = tags;
    }

    public static CeoStoreImageResponse of(StoreImage storeImage) {
        return CeoStoreImageResponse.builder()
                .id(storeImage.getId())
                .imageUrl(storeImage.getImageUrl())
                .sortOrder(storeImage.getSortOrder())
                .tags(storeImage.getTags())
                .build();
    }
}
