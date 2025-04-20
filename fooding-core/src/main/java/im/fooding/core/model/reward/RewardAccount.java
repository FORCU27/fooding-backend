package im.fooding.core.model.reward;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table( name = "reward_account" )
public class RewardAccount extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @ManyToOne
    @JoinColumn( name = "store_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) )
    private Store store;

    @Column( name = "phone_number" )
    private String phoneNumber;

    @ManyToOne
    @JoinColumn( name = "user_id" )
    private User user;

    @Column( name = "point" )
    private int point;

    public void addPoint( int earnPoint ){
        this.point += earnPoint;
    }
}
