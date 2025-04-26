package im.fooding.core.model.file;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class File extends BaseEntity {
    @Id
    private String id;

    private String originalName;

    private String storedName;

    private String url;

    private Long size;

    @Builder
    public File(String id, String originalName, String storedName, String url, Long size) {
        this.id = id;
        this.originalName = originalName;
        this.storedName = storedName;
        this.url = url;
        this.size = size;
    }
}
