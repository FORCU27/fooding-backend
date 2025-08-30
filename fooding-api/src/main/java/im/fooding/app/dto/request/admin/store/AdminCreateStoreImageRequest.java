package im.fooding.app.dto.request.admin.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminCreateStoreImageRequest {
    @Schema(description = "스토어 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "스토어 ID는 필수입니다.")
    @Positive(message = "스토어 ID는 양수여야 합니다.")
    private Long storeId;

    @Schema(description = "이미지 URL", example = "https://example.com/image.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이미지 URL은 필수입니다.")
    private String imageUrl;

    @Schema(description = "정렬순서", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "정렬순서는 필수입니다.")
    private Integer sortOrder;

    @Schema(description = "태그", example = "업체,음식")
    private String tags;
}
