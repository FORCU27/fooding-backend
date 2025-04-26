package im.fooding.core.service.file;

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

    public void create(String id, String originalName, String storedName, String url, long size) {
        File file = File.builder()
                .id(id)
                .originalName(originalName)
                .storedName(storedName)
                .url(url)
                .size(size)
                .build();
        repository.save(file);
    }
}
