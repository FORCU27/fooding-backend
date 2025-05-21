package im.fooding.app.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthUpdateProfileImageRequest {
    @Schema(description = "이미지 업로드 후 받은 id", example = "819f4bca-2739-46ca-9156-332c86eda619")
    private String imageId;

    public AuthUpdateProfileImageRequest(String imageId) {
        this.imageId = imageId;
    }
}
