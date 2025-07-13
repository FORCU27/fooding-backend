package im.fooding.app.dto.request.ceo.store.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoUpdateStoreImageRequest {
    @Schema(description = "이미지 업로드 후 받은 id", example = "819f4bca-2739-46ca-9156-332c86eda619")
    private String imageId;

    @Schema(description = "정렬 순서", example = "1")
    private int sortOrder;

    @Schema(description = "태그", example = "업체,음식")
    private String tags;
}
