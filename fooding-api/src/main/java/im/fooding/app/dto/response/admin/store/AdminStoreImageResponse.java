package im.fooding.app.dto.response.admin.store;

import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.model.store.StoreImageTag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdminStoreImageResponse {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "스토어 ID", example = "1")
    private Long storeId;

    @Schema(description = "이미지 url", example = "https://..", requiredMode = RequiredMode.REQUIRED)
    private String imageUrl;

    @Schema(description = "정렬순서", example = "1", requiredMode = RequiredMode.REQUIRED)
    private int sortOrder;

    @Schema(description = "태그", example = "[\"PRICE_TAG\", \"FOOD\", \"BEVERAGE\", \"INTERIOR\", \"EXTERIOR\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<StoreImageTag> tags;

    @Schema(description = "대표사진 여부", example = "false", requiredMode = RequiredMode.REQUIRED)
    private Boolean isMain;

    @Schema(description = "생성일시")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;

    @Builder
    private AdminStoreImageResponse(Long id, Long storeId, String imageUrl, int sortOrder, List<StoreImageTag> tags,
                                 Boolean isMain, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.storeId = storeId;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.tags = tags;
        this.isMain = isMain;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AdminStoreImageResponse of(StoreImage storeImage) {
        List<StoreImageTag> tags = null;
        if (StringUtils.hasText(storeImage.getTags())) {
            tags = Util.generateStringToList(storeImage.getTags()).stream()
                    .map(StoreImageTag::valueOf)
                    .toList();
        }
        return AdminStoreImageResponse.builder()
                .id(storeImage.getId())
                .storeId(storeImage.getStore().getId())
                .imageUrl(storeImage.getImageUrl())
                .sortOrder(storeImage.getSortOrder())
                .tags(tags)
                .isMain(storeImage.isMain())
                .createdAt(storeImage.getCreatedAt())
                .updatedAt(storeImage.getUpdatedAt())
                .build();
    }
}
