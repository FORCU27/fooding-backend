package im.fooding.app.dto.response.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponse {
    @Schema(description = "id", example = "819f4bca-2739-46ca-9156-332c86eda619")
    private String id;

    @Schema(description = "파일명", example = "819f4bca-2739-46ca-9156-332c86eda619")
    private String name;

    @Schema(description = "url", example = "https://d27gz6v6wvae1d.cloudfront.net/fooding/gigs/...")
    private String url;

    @Schema(description = "크기", example = "68813")
    private long size;

    @Builder
    public FileResponse(String id, String name, String url, long size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.size = size;
    }
}
