package im.fooding.core.global.util;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileValidator {
    private static final List<String> IMAGE_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ApiException(ErrorCode.FILE_NOT_FOUND);
        }

        String contentType = file.getContentType();

        if (isImage(contentType)) {
            if (file.getSize() > MAX_IMAGE_SIZE) {
                throw new ApiException(ErrorCode.FILE_SIZE_INVALID);
            }
        } else {
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new ApiException(ErrorCode.FILE_SIZE_INVALID);
            }
        }
    }

    private static boolean isImage(String contentType) {
        return contentType != null && IMAGE_CONTENT_TYPES.contains(contentType.toLowerCase());
    }
}
