package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.subway.SubwayStation;
import im.fooding.core.model.user.User;
import jakarta.persistence.*;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

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

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price_category", nullable = false)
    private String priceCategory;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private String information;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isParkingAvailable;

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

    @OneToMany(mappedBy = "store")
    @BatchSize(size = 10) // 한 번에 10개씩 배치로 로딩
    private List<StoreImage> images;

    @Builder
    private Store(
            User owner,
            String name,
            Region region,
            String city,
            String address,
            String category,
            String description,
            String contactNumber,
            String priceCategory,
            String eventDescription,
            String direction,
            String information,
            boolean isParkingAvailable,
            boolean isNewOpen,
            boolean isTakeOut,
            Double latitude,
            Double longitude
    ) {
        this.owner = owner;
        this.name = name;
        this.region = region;
        this.city = city;
        this.address = address;
        this.category = category;
        this.description = description;
        this.contactNumber = contactNumber;
        this.priceCategory = priceCategory;
        this.eventDescription = eventDescription;
        this.direction = direction;
        this.information = information;
        this.isParkingAvailable = isParkingAvailable;
        this.isNewOpen = isNewOpen;
        this.isTakeOut = isTakeOut;
        this.latitude = latitude;
        this.longitude = longitude;
        this.subwayStations = null;
    }

    public void update(
            String name,
            Region region,
            String city,
            String address,
            String category,
            String description,
            String contactNumber,
            String priceCategory,
            String eventDescription,
            String direction,
            String information,
            boolean isParkingAvailable,
            boolean isNewOpen,
            boolean isTakeOut,
            Double latitude,
            Double longitude
    ) {
        this.name = name;
        this.region = region;
        this.city = city;
        this.address = address;
        this.category = category;
        this.description = description;
        this.contactNumber = contactNumber;
        this.priceCategory = priceCategory;
        this.eventDescription = eventDescription;
        this.direction = direction;
        this.information = information;
        this.isParkingAvailable = isParkingAvailable;
        this.isNewOpen = isNewOpen;
        this.isTakeOut = isTakeOut;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public void setNearSubwayStations(List<SubwayStation> stations){
        this.subwayStations = stations;
    }
}
