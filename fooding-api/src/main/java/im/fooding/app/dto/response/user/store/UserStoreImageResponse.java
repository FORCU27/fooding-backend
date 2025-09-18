package im.fooding.app.dto.response.user.store;

import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.model.store.StoreImageTag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserStoreImageResponse {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "이미지 url", example = "https://..", requiredMode = RequiredMode.REQUIRED)
    private String imageUrl;

    @Schema(description = "정렬순서", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int sortOrder;

    @Schema(description = "태그", example = "[\"PRICE_TAG\", \"FOOD\", \"BEVERAGE\", \"INTERIOR\", \"EXTERIOR\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<StoreImageTag> tags;

    @Builder
    private UserStoreImageResponse(Long id, String imageUrl, int sortOrder, List<StoreImageTag> tags) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.tags = tags;
    }

    public static UserStoreImageResponse of(StoreImage storeImage) {
        List<StoreImageTag> tags = null;
        if (StringUtils.hasText(storeImage.getTags())) {
            tags = Util.generateStringToList(storeImage.getTags()).stream()
                    .map(StoreImageTag::valueOf)
                    .toList();
        }
        return UserStoreImageResponse.builder()
                .id(storeImage.getId())
                .imageUrl(storeImage.getImageUrl())
                .sortOrder(storeImage.getSortOrder())
                .tags(tags)
                .build();
    }
}
