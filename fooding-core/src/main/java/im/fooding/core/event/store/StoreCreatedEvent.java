package im.fooding.core.event.store;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.store.document.GeoPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreatedEvent {
    private Long id;

    private String name;

    private StoreCategory category;

    private String address;

    private int reviewCount;

    private double averageRating;

    private int visitCount;

    private String regionId;

    private StoreStatus status;

    private int averagePrice;

    private GeoPoint location;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    public StoreCreatedEvent(Long id) {
        this.id = id;
    }

    public StoreCreatedEvent(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.category = store.getCategory();
        this.address = store.getAddress();
        this.reviewCount = store.getReviewCount();
        this.averageRating = store.getAverageRating();
        this.visitCount = store.getVisitCount();
        this.regionId = store.getRegionId();
        this.status = store.getStatus();
        this.averagePrice = store.getAveragePrice();
        if (store.getLatitude() != null && store.getLongitude() != null) {
            this.location = new GeoPoint(store.getLatitude(), store.getLongitude());
        }
        this.createdAt = store.getCreatedAt();
    }
}
