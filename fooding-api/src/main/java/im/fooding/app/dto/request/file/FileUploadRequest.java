package im.fooding.app.dto.request.file;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class FileUploadRequest {
    @NotNull
    @Schema(description = "파일", requiredMode = RequiredMode.REQUIRED, example = "첨부파일")
    private MultipartFile[] files;

    public FileUploadRequest(MultipartFile[] files) {
        this.files = files;
    }
}
