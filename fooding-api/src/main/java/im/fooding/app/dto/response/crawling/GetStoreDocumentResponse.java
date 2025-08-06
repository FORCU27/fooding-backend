package im.fooding.app.dto.response.crawling;

import im.fooding.core.model.store.document.StoreDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetStoreDocumentResponse {
    private long id;
    private String name;
    private String category;
    private String address;
    private int reviewCount;
    private double averageRating;
    private int visitCount;
    private LocalDateTime createdAt;

    public static GetStoreDocumentResponse of(StoreDocument document){
        return GetStoreDocumentResponse.builder()
                .id(document.getId())
                .name(document.getName())
                .category(document.getCategory())
                .address(document.getAddress())
                .reviewCount(document.getReviewCount())
                .averageRating(document.getAverageRating())
                .visitCount(document.getVisitCount())
                .createdAt(document.getCreatedAt())
                .build();
    }
}
