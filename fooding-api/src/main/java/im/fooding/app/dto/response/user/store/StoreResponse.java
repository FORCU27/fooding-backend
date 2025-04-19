package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponse {

    @Schema(description = "id", example = "1" )
    private Long id;

    @Schema(description = "가게명", example = "홍길동 식당" )
    private String name;

    @Schema(description = "가게가 위치한 도시", example = "합정" )
    private String city;

    @Schema(description = "해당 가게의 총 리뷰 평점", example = "4.5" )
    private float reviewScore;

    @Schema(description = "해당 가게의 총 리뷰 개수", example = "246" )
    private int reviewCount;

    @Schema(description = "해당 가게의 평균 대기 시간", example = "20" )
    private int estimatedWaitingTimeMinutes;

    @Builder
    private StoreResponse(
            Long id,
            String name,
            String city,
            float reviewScore,
            int reviewCount,
            int estimatedWaitingTimeMinutes
    ) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.reviewScore = reviewScore;
        this.reviewCount = reviewCount;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
    }

    public static StoreResponse of(Store store, float reviewScore, int estimatedWaitingTime) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .city(store.getCity())
                .reviewScore(reviewScore)
                .reviewCount(store.getReviews().size())
                .estimatedWaitingTimeMinutes(estimatedWaitingTime)
                .build();
    }
}
