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

    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int commentCount;

    @Column(nullable = false)
    private int viewCount;

    @ColumnDefault("true")
    @Column(nullable = false)
    private boolean isCommentAvailable;

    @OneToMany(mappedBy = "storePost")
    @BatchSize(size = 10)
    private List<StorePostImage> images;

    @Builder
    public StorePost(Store store, String title, String content, List<String> tags, boolean isFixed, boolean isNotice, boolean isCommentAvailable) {
        this.store = store;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.isActive = true;
        this.isFixed = isFixed;
        this.isNotice = isNotice;
        this.isCommentAvailable = isCommentAvailable;
        this.likeCount = 0;
        this.commentCount = 0;
    }

    public void update(String title, String content, List<String> tags, boolean isFixed, boolean isNotice, boolean isCommentAvailable) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.isFixed = isFixed;
        this.isNotice = isNotice;
        this.isCommentAvailable = isCommentAvailable;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        this.commentCount--;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void decreaseViewCount() {
        this.viewCount--;
    }

    public void active() {
        this.isActive = true;
    }

    public void inactive() {
        this.isActive = false;
    }
}
