package im.fooding.app.dto.response.ceo.menu;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CeoMenuImageResponse {

    @Schema(description = "메뉴 이미지 ID", requiredMode = REQUIRED, example = "819f4bca-2739-46ca-9156-332c86eda619")
    String id;

    @Schema(description = "메뉴 이미지 URL", requiredMode = REQUIRED, example = "https://d27gz6v6wvae1d.cloudfront.net/fooding/gigs/...")
    String url;

    public CeoMenuImageResponse(String id, String url) {
        this.id = id;
        this.url = url;
    }
}
