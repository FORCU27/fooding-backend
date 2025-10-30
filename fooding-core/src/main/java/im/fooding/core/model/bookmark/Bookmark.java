package im.fooding.core.model.bookmark;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    private int verifiedCount;

    private Boolean isStarred;

    @Builder
    public Bookmark(Store store, User user) {
        this.store = store;
        this.user = user;
        this.isStarred = false;
    }

    public void increaseVerifiedCount() {
        this.verifiedCount++;
    }

    public void updateStarred(boolean isStarred) {
        this.isStarred = isStarred;
    }
}
