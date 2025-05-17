package im.fooding.core.global.feign.dto.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StorageResponse {
    private String id;
    private String fileName;
    private String fileKey;
    private long fileSize;
    private String mimeType;
    private boolean isPublic;
    private String bucketId;
    private String url;
    private String publicUrl;

    public StorageResponse(String id, String fileName, String fileKey, long fileSize, String mimeType, boolean isPublic, String bucketId, String url, String publicUrl) {
        this.id = id;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.isPublic = isPublic;
        this.bucketId = bucketId;
        this.url = url;
        this.publicUrl = publicUrl;
    }
}
