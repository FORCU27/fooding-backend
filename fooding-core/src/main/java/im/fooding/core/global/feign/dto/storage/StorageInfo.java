package im.fooding.core.global.feign.dto.storage;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@Getter
public class StorageInfo {
    private final String baseUri;
    private final String bucketId;
    private final String accessToken;

    public StorageInfo(@Value("${storage.api.bucket-id:null}") String bucketId,
                       @Value("${storage.api.token:null}") String accessToken) {
        this.baseUri = "https://storage-api.zerocloud.im/objects";
        this.bucketId = bucketId;
        this.accessToken = accessToken;
    }

    public URI getUploadUri() {
        return UriComponentsBuilder
                .fromHttpUrl(baseUri)
                .path("/upload/public")
                .queryParam("bucketId", bucketId)
                .build()
                .toUri();
    }

    public URI getCommitUri(String id) {
        return UriComponentsBuilder
                .fromHttpUrl(baseUri)
                .pathSegment(id, "commit")
                .build()
                .toUri();
    }
}
