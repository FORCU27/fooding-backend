package im.fooding.core.model.store;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.waiting.Waiting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    // TODO : 추후 VO 설계
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
    @JsonProperty("isParkingAvailable")
    private boolean isParkingAvailable;

    @Column(nullable = false)
    @JsonProperty("isNewOpen")
    private boolean isNewOpen;

    @Column(nullable = false)
    @JsonProperty("isTakeOut")
    private boolean isTakeOut;

    @Builder
    private Store(
            String name,
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
            boolean isTakeOut) {
        this.name = name;
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
    }

    public void updateStoreName(String name) {
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateContactNumber(String contactNumber) {
        this.contactNumber = this.contactNumber;
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

    public void updateAddress(String address) {
        this.address = address;
    }

    public void delete() {
        // TODO: soft delete 추가
    }
}
