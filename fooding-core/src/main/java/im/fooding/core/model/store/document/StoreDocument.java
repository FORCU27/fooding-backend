package im.fooding.core.model.store.document;

import im.fooding.core.model.store.Store;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Getter
@Document(indexName = "stores_v1", createIndex = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDocument {
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Integer)
    private int reviewCount;

    @Field(type = FieldType.Double)
    private double averageRating;

    @Field(type = FieldType.Integer)
    private int visitCount;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdAt;

    @Builder
    public StoreDocument(Long id, String name, String category, String address, int reviewCount, double averageRating, int visitCount, LocalDateTime createdAt) {
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
}
