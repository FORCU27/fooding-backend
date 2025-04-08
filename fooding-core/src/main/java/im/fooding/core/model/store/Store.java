package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price_category", nullable = false)
    private String priceCategory;

    @Column(name = "event_description")
    private String eventDescription;

    @Column(name = "store_number", nullable = false)
    private String storeNumber;

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

    @Builder
    public Store(
            String name,
            String city,
            String category,
            String description,
            String storeNumber,
            String priceCategory,
            String eventDescription,
            String direction,
            String information,
            boolean isParkingAvailable,
            boolean isNewOpen,
            boolean isTakeOut
    ) {
        this.name = name;
        this.city = city;
        this.category = category;
        this.description = description;
        this.storeNumber = storeNumber;
        this.priceCategory = priceCategory;
        this.eventDescription = eventDescription;
        this.direction = direction;
        this.information = information;
        this.isParkingAvailable = isParkingAvailable;
        this.isNewOpen = isNewOpen;
        this.isTakeOut = isTakeOut;
    }

    public void updateStoreName(String name) {
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public void updatePriceCategory(String priceCategory) {
        this.priceCategory = priceCategory;
    }

    public void updateDirection(String direction) {
        this.direction = direction;
    }

    public void updateInformation(String information) {
        this.information = information;
    }

    public void updateParkingAvailability(boolean isParkingAvailable) {
        this.isParkingAvailable = isParkingAvailable;
    }

    public void updateIsNewOpen(boolean isNewOpen) {
        this.isNewOpen = isNewOpen;
    }

    public void updateIsTakeOut(boolean isTakeOut) {
        this.isTakeOut = isTakeOut;
    }

    public void updateCategory(String category) {
        this.category = category;
    }

    public void updateCity(String city) {
        this.city = city;
    }

    public void updateEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
