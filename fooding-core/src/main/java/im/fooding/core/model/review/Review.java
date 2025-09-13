package im.fooding.core.model.review;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.transaction.annotation.Transactional;

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
    }

    @Transactional
    public void update(String content, VisitPurposeType visitPurposeType, ReviewScore score) {
        this.content = content;
        this.visitPurposeType = visitPurposeType;
        this.score = score;
    }

    @Transactional
    public void setBlind( boolean isBlind ) { this.isBlind = isBlind; }
}
