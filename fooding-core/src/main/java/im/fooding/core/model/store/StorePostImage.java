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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StorePostImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "store_post_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @Setter
    private StorePost storePost;

    @Column(nullable = false)
    private String imageId;

    @Column(nullable = false)
    private String imageUrl;

    public StorePostImage(StorePost storePost, String imageId, String imageUrl) {
        this.storePost = storePost;
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public void update(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

