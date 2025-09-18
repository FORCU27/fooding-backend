package im.fooding.core.model.store.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDocument {
    private Long id;

    private String name;

    private String category;

    private String address;

    private int reviewCount;

    private double averageRating;

    private int visitCount;

    private String regionId;

    private String status;

    private int averagePrice;

    private GeoPoint location;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @Builder
    private StoreDocument(Long id, String name, String category, String address, int reviewCount, double averageRating, int visitCount,
                          String regionId, String status, int averagePrice, GeoPoint location, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.visitCount = visitCount;
        this.regionId = regionId;
        this.status = status;
        this.averagePrice = averagePrice;
        this.location = location;
        this.createdAt = createdAt;
    }

    public String getIndex() {
        return "stores_v1";
    }
}

