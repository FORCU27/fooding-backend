package im.fooding.core.dto.response;

import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.model.store.StoreImageTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@NoArgsConstructor
public class StoreImageResponse {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "이미지 url", example = "https://..", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imageUrl;

    @Schema(description = "정렬순서", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int sortOrder;

    @Schema(description = "태그", example = "[\"PRICE_TAG\", \"FOOD\", \"BEVERAGE\", \"INTERIOR\", \"EXTERIOR\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<StoreImageTag> tags;

    @Schema(description = "대표사진 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isMain;

    @Builder
    private StoreImageResponse(Long id, String imageUrl, int sortOrder, List<StoreImageTag> tags, Boolean isMain) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.tags = tags;
        this.isMain = isMain;
    }

    public static StoreImageResponse of(StoreImage storeImage) {
        List<StoreImageTag> tags = null;
        if (StringUtils.hasText(storeImage.getTags())) {
            tags = Util.generateStringToList(storeImage.getTags()).stream()
                    .map(StoreImageTag::valueOf)
                    .toList();
        }
        return StoreImageResponse.builder()
                .id(storeImage.getId())
                .imageUrl(storeImage.getImageUrl())
                .sortOrder(storeImage.getSortOrder())
                .tags(tags)
                .isMain(storeImage.isMain())
                .build();
    }
}
