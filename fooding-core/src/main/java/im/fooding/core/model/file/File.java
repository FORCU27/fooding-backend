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

    private String name;

    private String url;

    private Long size;

    private boolean committed;

    @Builder
    public File(String id, String name, String url, Long size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.size = size;
    }

    public void commit() {
        this.committed = true;
    }
}
