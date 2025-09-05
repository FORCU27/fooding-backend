package im.fooding.app.dto.response.admin.store;

import im.fooding.core.model.store.StoreImage;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminStoreImageResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "이미지 url", example = "https://..", requiredMode = RequiredMode.REQUIRED)
    private String imageUrl;

    @Schema(description = "정렬순서", example = "1", requiredMode = RequiredMode.NOT_REQUIRED)
    private int sortOrder;

    @Schema(description = "태그", example = "업체,음식", requiredMode = RequiredMode.NOT_REQUIRED)
    private String tags;

    @Builder
    private AdminStoreImageResponse(Long id, String imageUrl, int sortOrder, String tags) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.tags = tags;
    }

    public static AdminStoreImageResponse of(StoreImage storeImage) {
        return AdminStoreImageResponse.builder()
                .id(storeImage.getId())
                .imageUrl(storeImage.getImageUrl())
                .sortOrder(storeImage.getSortOrder())
                .tags(storeImage.getTags())
                .build();
    }
}

