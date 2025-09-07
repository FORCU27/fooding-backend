package im.fooding.core.model.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.subway.SubwayStation;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User owner;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Region region;

    @Column(name = "address", nullable = false)
    private String address;

    private String addressDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private StoreCategory category;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private boolean isNewOpen;

    @Column(nullable = false)
    private boolean isTakeOut;

    private int reviewCount;

    private int visitCount;

    private double averageRating;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<SubwayStation> subwayStations;

    private Double latitude;

    private Double longitude;

    private int bookmarkCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    private StoreStatus status;

    @OneToMany(mappedBy = "store")
    @BatchSize(size = 10) // 한 번에 10개씩 배치로 로딩
    private List<StoreImage> images;

    @Builder
    private Store(User owner,
                  String name,
                  Region region,
                  String address,
                  String addressDetail,
                  StoreCategory category,
                  String description,
                  String contactNumber,
                  String direction,
                  boolean isNewOpen,
                  boolean isTakeOut,
                  Double latitude,
                  Double longitude,
                  StoreStatus status
    ) {
        this.owner = owner;
        this.name = name;
        this.region = region;
        this.address = address;
        this.addressDetail = addressDetail;
        this.category = category;
        this.description = description;
        this.contactNumber = contactNumber;
        this.direction = direction;
        this.isNewOpen = isNewOpen;
        this.isTakeOut = isTakeOut;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status != null ? status : StoreStatus.PENDING;
        this.subwayStations = null;
    }

    public void update(
            String name,
            Region region,
            String address,
            String addressDetail,
            StoreCategory category,
            String description,
            String contactNumber,
            String direction,
            boolean isNewOpen,
            boolean isTakeOut,
            Double latitude,
            Double longitude,
            StoreStatus status
    ) {
        this.name = name;
        this.region = region;
        this.address = address;
        this.addressDetail = addressDetail;
        this.category = category;
        this.description = description;
        this.contactNumber = contactNumber;
        this.direction = direction;
        this.isNewOpen = isNewOpen;
        this.isTakeOut = isTakeOut;
        this.latitude = latitude;
        this.longitude = longitude;
        if (status != null) {
            this.status = status;
        }
    }

    public void increaseVisitCount() {
        this.visitCount++;
    }

    public void increaseReviewCount() {
        this.reviewCount++;
    }

    public void decreaseReviewCount() {
        this.reviewCount--;
    }

    public void updateAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void increaseBookmarkCount() {
        this.bookmarkCount++;
    }

    public void decreaseBookmarkCount() {
        this.bookmarkCount--;
    }
    
    public void updateStatus(StoreStatus status) {
        this.status = status;
    }

    public void approve() {
        // 필수값(주소, 카테고리, 연락처, 위도, 경도)
        if (!StringUtils.hasText(address) ||  null == category || !StringUtils.hasText(contactNumber) || null == latitude || null == longitude) {
            throw new ApiException(ErrorCode.STORE_APPROVED_FAILED);
        }
        this.status = StoreStatus.APPROVED;
    }

    public void reject() {
        this.status = StoreStatus.REJECTED;
    }

    public void suspend() {
        this.status = StoreStatus.SUSPENDED;
    }

    public void close() {
        this.status = StoreStatus.CLOSED;
    }

    public void setPending() {
        this.status = StoreStatus.PENDING;
    }

    public boolean isPending() {
        return this.status == StoreStatus.PENDING;
    }

    public boolean isApproved() {
        return this.status == StoreStatus.APPROVED;
    }

    public boolean isRejected() {
        return this.status == StoreStatus.REJECTED;
    }

    public boolean isSuspended() {
        return this.status == StoreStatus.SUSPENDED;
    }

    public boolean isClosed() {
        return this.status == StoreStatus.CLOSED;
    }

    public void setNearSubwayStations(List<SubwayStation> stations) {
        this.subwayStations = stations;
    }

    public String getRegionId() {
        if (region == null) {
            return null;
        }
        return region.getId();
    }
}
