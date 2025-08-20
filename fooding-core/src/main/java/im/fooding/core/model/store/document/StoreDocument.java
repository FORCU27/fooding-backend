package im.fooding.core.model.store.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.event.store.StoreCreatedEvent;
import im.fooding.core.model.store.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    @Builder
    private StoreDocument(Long id, String name, String category, String address, int reviewCount, double averageRating, int visitCount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.visitCount = visitCount;
        this.createdAt = createdAt;
    }

    public static StoreDocument from(Store store) {
        return StoreDocument.builder()
                .id(store.getId())
                .name(store.getName())
                .category(store.getCategory())
                .address(store.getAddress())
                .reviewCount(store.getReviewCount())
                .averageRating(store.getAverageRating())
                .visitCount(store.getVisitCount())
                .createdAt(store.getCreatedAt())
                .build();
    }

    public static StoreDocument of(StoreCreatedEvent event) {
        return StoreDocument.builder()
                .id(event.getId())
                .name(event.getName())
                .category(event.getCategory())
                .address(event.getAddress())
                .reviewCount(event.getReviewCount())
                .averageRating(event.getAverageRating())
                .visitCount(event.getVisitCount())
                .createdAt(event.getCreatedAt())
                .build();
    }


    public String getIndex() {
        return "stores_v1";
    }
}
