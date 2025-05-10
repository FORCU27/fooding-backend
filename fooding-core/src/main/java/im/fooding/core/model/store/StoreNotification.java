package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreNotification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String linkUrl;

    @Builder
    public StoreNotification(Store store, String title, String content, String category, String linkUrl) {
      this.store = store;
      this.title = title;
      this.content = content;
      this.category = category;
      this.linkUrl = linkUrl;
    }

    public void update(String title, String content, String category, String linkUrl) {
      this.title = title;
      this.content = content;
      this.category = category;
      this.linkUrl = linkUrl;
    }
}