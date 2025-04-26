package im.fooding.app.controller.file;

import im.fooding.app.dto.request.file.FileUploadRequest;
import im.fooding.app.dto.response.file.FileResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@Tag(name = "FileUploadController", description = "파일 컨트롤러")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    @Operation(summary = "파일 업로드", description = "파일을  업로드합니다")
    public ApiResult<List<FileResponse>> upload(@Valid FileUploadRequest request) {
        return fileUploadService.upload(request);
    }
}
