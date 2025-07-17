package im.fooding.core.model.reward;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
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
@Table(name = "reward_point")
public class RewardPoint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "point")
    private int point;

    public void addPoint(int earnPoint) {
        this.point += earnPoint;
    }

    public void usePoint(int usePoint) {
        if (this.point < usePoint) {
            throw new ApiException(ErrorCode.REWARD_POINT_NOT_ENOUGH);
        }
        this.point -= usePoint;
    }

    @Builder
    public RewardPoint(Store store, String phoneNumber, User user, int point) {
        this.store = store;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.point = point;
    }
}
