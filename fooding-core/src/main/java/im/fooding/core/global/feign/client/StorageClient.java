package im.fooding.core.global.feign.client;

import im.fooding.core.global.feign.dto.storage.StorageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@FeignClient(name = "storageClient")
public interface StorageClient {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    StorageResponse upload(URI baseUrl,
                           @RequestHeader("x-bucket-token") String authorization,
                           @RequestPart("file") MultipartFile file);

    @PostMapping
    void commit(URI baseUrl,
                @RequestHeader("x-bucket-token") String authorization);
}
