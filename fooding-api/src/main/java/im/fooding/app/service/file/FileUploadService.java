package im.fooding.app.service.file;

import im.fooding.app.dto.request.file.FileUploadRequest;
import im.fooding.app.dto.response.file.FileResponse;
import im.fooding.core.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {
    private final FileService fileService;

    public List<FileResponse> upload(FileUploadRequest request) {
//        fileService.create();
        return null;
    }
}
