package im.fooding.app.dto.response.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponse {
    @Schema(description = "id", example = "uuid")
    private Long id;

    @Schema(description = "파일명", example = "11.png")
    private String name;

    @Schema(description = "url", example = "https://...")
    private String url;

    @Schema(description = "파일 사이즈(byte)", example = "7012")
    private Long fileSize;

    public FileResponse(Long id, String name, String url, Long fileSize) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.fileSize = fileSize;
    }
}
