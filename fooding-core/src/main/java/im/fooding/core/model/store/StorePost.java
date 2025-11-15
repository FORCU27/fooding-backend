package im.fooding.core.model.store;

import im.fooding.core.common.StringListConverter;
import im.fooding.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StorePost extends BaseEntity {
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

    @Convert(converter = StringListConverter.class)
    @Column(name = "tags")
    private List<String> tags;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isFixed;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean isNotice;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int commentCount;

    @OneToMany(mappedBy = "storePost")
    @BatchSize(size = 10)
    private List<StorePostImage> images;

    @Builder
    public StorePost(Store store, String title, String content, List<String> tags, boolean isFixed, boolean isNotice) {
      this.store = store;
      this.title = title;
      this.content = content;
      this.tags = tags;
      this.isFixed = isFixed;
      this.isNotice = isNotice;
      this.likeCount = 0;
      this.commentCount = 0;
    }

    public void update(String title, String content, List<String> tags, boolean isFixed, boolean isNotice) {
      this.title = title;
      this.content = content;
      this.tags = tags;
      this.isFixed = isFixed;
      this.isNotice = isNotice;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }
}
