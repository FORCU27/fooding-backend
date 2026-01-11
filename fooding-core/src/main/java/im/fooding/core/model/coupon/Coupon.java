package im.fooding.core.model.coupon;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
public class Coupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BenefitType benefitType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProvideType provideType;

    @Column(nullable = false, length = 50)
    private String name;

    private String conditions;

    private Integer totalQuantity;

    private int issuedQuantity;

    private int discountValue;

    private LocalDate issueStartOn;

    private LocalDate issueEndOn;

    private LocalDate expiredOn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;

    @Builder
    public Coupon(Store store, BenefitType benefitType, CouponType type, DiscountType discountType, ProvideType provideType,
        String name, String conditions, Integer totalQuantity, int discountValue, LocalDate issueStartOn, LocalDate issueEndOn,
        LocalDate expiredOn, CouponStatus status) {
        // 할인 타입일 경우 할인 금액 0보다 커야함.
        if (BenefitType.DISCOUNT == benefitType) {
            if (discountValue <= 0) {
                throw new ApiException(ErrorCode.COUPON_DISCOUNT_VALUE_INVALID);
            }
        }
        this.store = store;
        this.benefitType = benefitType;
        this.type = type;
        this.discountType = discountType;
        this.provideType = provideType;
        this.name = name;
        this.conditions = conditions;
        this.totalQuantity = totalQuantity;
        this.discountValue = discountValue;
        this.issueStartOn = issueStartOn;
        this.issueEndOn = issueEndOn;
        this.expiredOn = expiredOn;
        this.status = status;
    }

    public void update(Store store, BenefitType benefitType, CouponType type, DiscountType discountType, ProvideType provideType,
        String name, String conditions, Integer totalQuantity, LocalDate issueStartOn, LocalDate issueEndOn, LocalDate expiredOn,
        int discountValue, CouponStatus status) {
        this.store = store;
        this.benefitType = benefitType;
        this.type = type;
        this.discountType = discountType;
        this.provideType = provideType;
        this.name = name;
        this.conditions = conditions;
        this.totalQuantity = totalQuantity;
        this.issueStartOn = issueStartOn;
        this.issueEndOn = issueEndOn;
        this.expiredOn = expiredOn;
        this.discountValue = discountValue;
        this.status = status;
    }

    public void issue() {
        if (!availableIssueQuantity()) {
            throw new ApiException(ErrorCode.COUPON_ISSUE_QUANTITY_INVALID);
        }
        if (!availableIssueDate()) {
            throw new ApiException(ErrorCode.COUPON_ISSUE_DATE_INVALID);
        }
        this.issuedQuantity++;
    }

    public boolean availableIssueQuantity() {
        if (this.totalQuantity == null) {
            return true;
        }
        return this.totalQuantity > this.issuedQuantity;
    }

    public boolean availableIssueDate() {
        LocalDate today = LocalDate.now();
        if (this.issueStartOn != null && this.issueEndOn != null) {
            // 둘 다 있을 경우
            return !today.isBefore(this.issueStartOn) && !today.isAfter(this.issueEndOn);
        } else if (this.issueStartOn != null) {
            // 시작일만 있을 경우
            return !today.isBefore(this.issueStartOn);
        } else if (this.issueEndOn != null) {
            // 마감일만 있을 경우
            return !today.isAfter(this.issueEndOn);
        } else {
            return true; // 모든 날짜 허용
        }
    }
}
