package im.fooding.core.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Persistent;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Persistent
public abstract class BaseDocument {

    @CreatedBy
    private Long createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedBy
    private Long updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private boolean deleted = false;

    private Long deletedBy;

    public void delete(Long deletedBy) {
        this.deleted = true;
        this.deletedBy = deletedBy;
    }
}
