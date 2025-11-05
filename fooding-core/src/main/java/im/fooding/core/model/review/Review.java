package im.fooding.core.model.review;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Review extends BaseEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_id",
            nullable = true,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Review parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "review")
    private List<Review> replies;

    @Embedded
    private ReviewScore score;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "purpose_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitPurposeType visitPurposeType;

    @Column(name = "isBlind", nullable = false)
    private boolean isBlind;

    @Builder
    private Review(
            Store store,
            User writer,
            ReviewScore score,
            String content,
            VisitPurposeType visitPurposeType
    ) {
        this.store = store;
        this.writer = writer;
        this.score = score;
        this.content = content;
        this.visitPurposeType = visitPurposeType;
        this.isBlind = false;
        this.replies = new ArrayList<>();
    }

    public void update(String content, VisitPurposeType visitPurposeType, ReviewScore score) {
        this.content = content;
        this.visitPurposeType = visitPurposeType;
        this.score = score;
    }

    public void setBlind( boolean isBlind ) { this.isBlind = isBlind; }

    public void setParent( Review parent ) {
        this.parent = parent;
        parent.replies.add(this);
    }
}
