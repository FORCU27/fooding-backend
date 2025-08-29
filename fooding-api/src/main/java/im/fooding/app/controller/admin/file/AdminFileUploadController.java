package im.fooding.app.controller.admin.file;

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
@RequestMapping("/admin/file-upload")
@Tag(name = "AdminFileUploadController", description = "[관리자] 파일 업로드 컨트롤러")
public class AdminFileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    @Operation(summary = "파일 업로드", description = "관리자 파일 업로드")
    public ApiResult<List<FileResponse>> upload(@Valid FileUploadRequest request) {
        return ApiResult.ok(fileUploadService.upload(request));
    }
}

