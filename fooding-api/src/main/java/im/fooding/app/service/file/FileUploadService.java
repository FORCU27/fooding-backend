package im.fooding.app.service.file;

import im.fooding.app.dto.request.file.FileUploadRequest;
import im.fooding.app.dto.response.file.FileResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.feign.client.StorageClient;
import im.fooding.core.global.feign.dto.storage.StorageInfo;
import im.fooding.core.global.feign.dto.storage.StorageResponse;
import im.fooding.core.model.file.File;
import im.fooding.core.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static im.fooding.core.global.util.FileValidator.validateFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {
    private final FileService fileService;
    private final StorageClient storageClient;
    private final StorageInfo storageInfo;

    @Transactional
    public List<FileResponse> upload(FileUploadRequest request) {
        List<FileResponse> files = new ArrayList<>();
        for (MultipartFile multipartFile : request.getFiles()) {
            validateFile(multipartFile);
            try {
                StorageResponse storageResponse = storageClient.upload(storageInfo.getUploadUri(), storageInfo.getAccessToken(), multipartFile);
                if (storageResponse != null) {
                    fileService.create(storageResponse.getId(), storageResponse.getFileName(), storageResponse.getPublicUrl(), storageResponse.getFileSize());
                    FileResponse fileResponse = FileResponse.builder()
                            .id(storageResponse.getId())
                            .url(storageResponse.getPublicUrl())
                            .name(multipartFile.getOriginalFilename())
                            .size(storageResponse.getFileSize())
                            .build();
                    files.add(fileResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApiException(ErrorCode.FILE_UPLOAD_FAILED, e.getMessage());
            }
        }
        return files;
    }

    public File commit(String id) {
        if (StringUtils.hasText(id)) {
            try {
                File file = fileService.commit(id);
                storageClient.commit(storageInfo.getCommitUri(id), storageInfo.getAccessToken());
                return file;
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApiException(ErrorCode.FILE_UPLOAD_FAILED, e.getMessage());
            }
        } else {
            return null;
        }
    }
}
