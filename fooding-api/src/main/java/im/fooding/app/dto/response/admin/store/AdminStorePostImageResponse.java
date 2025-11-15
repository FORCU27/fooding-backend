package im.fooding.app.dto.response.admin.store;

import im.fooding.core.model.store.StorePostImage;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Value;

@Value
public class AdminStorePostImageResponse {
    @Schema(description = "image ID", requiredMode = RequiredMode.REQUIRED, example = "002f4860-3b13-4033-8b5f-6cf91a9816e7")
    String imageId;

    @Schema(description = "image URL", requiredMode = RequiredMode.REQUIRED, example = "https://...")
    String imageUrl;

    public static AdminStorePostImageResponse from(StorePostImage storePostImage) {
        return new AdminStorePostImageResponse(
                storePostImage.getImageId(),
                storePostImage.getImageUrl()
        );
    }
}
