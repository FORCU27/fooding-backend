package im.fooding.core.model.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StorePostComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_post_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StorePost storePost;

    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private StorePostComment parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private User writer;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @ColumnDefault("false")
    private boolean isOwner;

    @ColumnDefault("false")
    private boolean isParentWriter;

    private int likeCount;

    private int depth;

    @OneToMany(mappedBy = "parent")
    private List<StorePostComment> replies = new ArrayList<>();

    @Builder
    public StorePostComment(StorePost storePost, StorePostComment parent, User writer, String content,
                            boolean isOwner) {
        this.storePost = storePost;
        this.parent = parent;
        this.writer = writer;
        this.content = content;
        this.isOwner = isOwner;
        this.depth = parent == null ? 1 : 2;
        if (parent != null) {
            // 최대 2 depth
            if (parent.getDepth() == 2) {
                throw new ApiException(ErrorCode.STORE_POST_COMMENT_MAX_COMMENT_DEPTH_EXCEEDED);
            }
            // 작성자 flag
            if (parent.getWriter().getId().equals(writer.getId())) {
                this.isParentWriter = true;
            }
        }
    }

    public void update(User writer, String content) {
        validateWriter(writer);
        this.content = content;
    }

    public void delete(User writer, Long deletedBy) {
        validateWriter(writer);
        this.delete(deletedBy);
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    private void validateWriter(User writer) {
        if (!Objects.equals(this.writer.getId(), writer.getId())) {
            throw new ApiException(ErrorCode.ACCESS_DENIED_EXCEPTION);
        }
    }
}

