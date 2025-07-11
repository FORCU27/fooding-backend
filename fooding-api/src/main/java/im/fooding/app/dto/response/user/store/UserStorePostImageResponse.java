package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.StorePostImage;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Value;

@Value
public class UserStorePostImageResponse {

    @Schema(description = "ID", requiredMode = RequiredMode.REQUIRED, example = "1")
    long id;

    @Schema(description = "image URL", requiredMode = RequiredMode.REQUIRED)
    String imageUrl;

    public static UserStorePostImageResponse from(StorePostImage storePostImage) {
        return new UserStorePostImageResponse(
                storePostImage.getId(),
                storePostImage.getImageUrl()
        );
    }
}
