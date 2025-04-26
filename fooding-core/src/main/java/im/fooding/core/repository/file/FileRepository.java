package im.fooding.core.repository.file;

import im.fooding.core.model.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
