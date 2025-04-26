package im.fooding.core.model.coupon;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    private Integer totalQuantity;

    private int issuedQuantity;

    private int discountAmount;

    private Integer minOrderAmount;

    private Integer maxDiscountAmount;

    private LocalDateTime issueStartAt;

    private LocalDateTime issueEndAt;

    private Integer expiredDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;
}
