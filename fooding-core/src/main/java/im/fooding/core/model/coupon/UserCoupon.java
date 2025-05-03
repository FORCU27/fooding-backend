package im.fooding.core.model.coupon;

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
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
public class UserCoupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BenefitType benefitType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    private int discountValue;

    @Column(nullable = false, length = 50)
    private String name;

    private String conditions;

    private boolean used;

    private LocalDateTime usedAt;

    private LocalDate expiredOn;

    @Builder
    public UserCoupon(Coupon coupon, User user, Store store, BenefitType benefitType, DiscountType discountType, int discountValue, String name, String conditions, LocalDate expiredOn) {
        this.coupon = coupon;
        this.user = user;
        this.store = store;
        this.benefitType = benefitType;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.name = name;
        this.conditions = conditions;
        this.expiredOn = expiredOn;
    }

    public void use() {
        if (!availableExpiredOn()) {
            throw new ApiException(ErrorCode.USER_COUPON_EXPIRED);
        }
        if (this.used) {
            throw new ApiException(ErrorCode.USER_COUPON_ALREADY_USE);
        }
        this.used = true;
        this.usedAt = LocalDateTime.now();
    }

    public boolean availableExpiredOn() {
        LocalDate today = LocalDate.now();
        return (this.expiredOn == null || !today.isAfter(this.expiredOn));
    }
}
