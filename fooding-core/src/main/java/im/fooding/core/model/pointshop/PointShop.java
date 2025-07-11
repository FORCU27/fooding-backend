package im.fooding.core.model.pointshop;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.coupon.ProvideType;
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
public class PointShop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    private String name;

    private int point;

    @Enumerated(EnumType.STRING)
    private ProvideType provideType;

    private String conditions;

    private Integer totalQuantity;

    private int issuedQuantity;

    private boolean isActive;

    private LocalDate issueStartOn;

    private LocalDate issueEndOn;

    @Builder
    public PointShop(Store store, String name, int point, ProvideType provideType, String conditions, Integer totalQuantity, boolean isActive, LocalDate issueStartOn, LocalDate issueEndOn) {
        this.store = store;
        this.name = name;
        this.point = point;
        this.provideType = provideType;
        this.conditions = conditions;
        this.totalQuantity = totalQuantity;
        this.isActive = isActive;
        this.issueStartOn = issueStartOn;
        this.issueEndOn = issueEndOn;
    }

    public void update(String name, int point, ProvideType provideType, String conditions, Integer totalQuantity, boolean isActive, LocalDate issueStartOn, LocalDate issueEndOn) {
        this.name = name;
        this.point = point;
        this.provideType = provideType;
        this.conditions = conditions;
        this.totalQuantity = totalQuantity;
        this.isActive = isActive;
        this.issueStartOn = issueStartOn;
        this.issueEndOn = issueEndOn;
    }

    public void issue() {
        if (!availableIssueQuantity()) {
            throw new ApiException(ErrorCode.POINT_SHOP_ISSUE_QUANTITY_INVALID);
        }
        if (!availableIssueDate()) {
            throw new ApiException(ErrorCode.POINT_SHOP_ISSUE_DATE_INVALID);
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
        boolean started = !today.isBefore(this.issueStartOn);
        boolean notEnded = (this.issueEndOn == null || !today.isAfter(this.issueEndOn));
        return started && notEnded;
    }
}
