package im.fooding.core.service.file;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.file.File;
import im.fooding.core.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final FileRepository repository;

    public void create(String id, String name, String url, long size) {
        File file = File.builder()
                .id(id)
                .name(name)
                .url(url)
                .size(size)
                .build();
        repository.save(file);
    }

    public void commit(String id) {
        File file = findById(id);
        file.commit();
    }

    public File findById(String id) {
        return repository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.FILE_NOT_FOUND));
    }
}
