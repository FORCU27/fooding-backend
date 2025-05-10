package im.fooding.app.dto.response.admin.store;

import im.fooding.core.model.store.Store;
import lombok.Getter;

@Getter
public class AdminStoreResponse {
    private final Long id;
    private final String name;
    private final String city;
    private final String address;
    private final String category;
    private final String description;
    private final String priceCategory;
    private final String eventDescription;
    private final String contactNumber;
    private final String direction;
    private final String information;
    private final boolean isParkingAvailable;
    private final boolean isNewOpen;
    private final boolean isTakeOut;

    public AdminStoreResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.city = store.getCity();
        this.address = store.getAddress();
        this.category = store.getCategory();
        this.description = store.getDescription();
        this.priceCategory = store.getPriceCategory();
        this.eventDescription = store.getEventDescription();
        this.contactNumber = store.getContactNumber();
        this.direction = store.getDirection();
        this.information = store.getInformation();
        this.isParkingAvailable = store.isParkingAvailable();
        this.isNewOpen = store.isNewOpen();
        this.isTakeOut = store.isTakeOut();
    }
}