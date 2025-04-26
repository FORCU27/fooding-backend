package im.fooding.app.dto.request.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class FileUploadRequest {
    @NotNull(message = "파일을 전송해주세요.")
    @Schema(description = "파일", example = "첨부파일")
    private MultipartFile[] files;
}
