package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStoreListResponse {
    @Schema(description = "id", example = "1" )
    private Long id;

    @Schema(description = "가게명", example = "홍길동 식당" )
    private String name;

    @Schema(description = "가게 이미지 URL", example = "https://example.com/store.jpg" )
    private String mainImage;

    @Schema(description = "가게가 위치한 도시", example = "합정" )
    private String city;

    @Schema(description = "해당 가게의 총 방문수", example = "1000" )
    private int visitCount;

    @Schema(description = "해당 가게의 총 리뷰 개수", example = "246" )
    private int reviewCount;

    @Schema(description = "해당 가게의 총 리뷰 평점", example = "4.5" )
    private double averageRating;

    @Schema(description = "해당 가게의 평균 대기 시간 (웨이팅이 비활성화된 가게일 경우, null 반환)", example = "20" )
    private Integer estimatedWaitingTimeMinutes;

    @Builder
    private UserStoreListResponse(Long id, String name, String image, String city, double averageRating, int visitCount,
                                  int reviewCount, Integer estimatedWaitingTimeMinutes) {
        this.id = id;
        this.name = name;
        this.mainImage = image;
        this.city = city;
        this.visitCount = visitCount;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
    }

    public static UserStoreListResponse of(Store store, Integer estimatedWaitingTime) {
        String imageUrl = store.getImages() != null ? store.getImages().stream().findFirst().map(StoreImage::getImageUrl).orElse(null) : null;
        return UserStoreListResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .image(imageUrl)
                .city(store.getCity())
                .visitCount(store.getVisitCount())
                .reviewCount(store.getReviewCount())
                .averageRating(store.getAverageRating())
                .estimatedWaitingTimeMinutes(estimatedWaitingTime)
                .build();
    }
}
