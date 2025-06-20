package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "store_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Store store;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int sortOrder;

    private String tags;

    @Builder
    private StoreImage(Store store, String imageUrl, int sortOrder, String tags) {
        this.store = store;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.tags = !StringUtils.hasText(tags) ? null : tags;
    }

    public void update(String imageUrl, int sortOrder, String tags) {
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.tags = !StringUtils.hasText(tags) ? null : tags;;
    }
}
