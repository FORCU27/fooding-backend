package im.fooding.app.dto.response.user.bookmark;

import im.fooding.core.dto.response.StoreImageResponse;
import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserBookmarkResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "가게 id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long storeId;

    @Schema(description = "가게명", example = "홍길동 식당", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "해당 가게의 총 방문수", example = "1000", requiredMode = RequiredMode.REQUIRED)
    private int visitCount;

    @Schema(description = "해당 가게의 총 리뷰 개수", example = "246", requiredMode = RequiredMode.REQUIRED)
    private int reviewCount;

    @Schema(description = "해당 가게의 총 단골수", example = "100", requiredMode = RequiredMode.REQUIRED)
    private int bookmarkCount;

    @Schema(description = "해당 가게의 총 리뷰 평점", example = "4.5", requiredMode = RequiredMode.REQUIRED)
    private double averageRating;

    @Schema(description = "해당 가게의 평균 대기 시간 (웨이팅이 비활성화된 가게일 경우, null 반환)", example = "20", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer estimatedWaitingTimeMinutes;

    @Schema(description = "영업 종료 여부", example = "false", requiredMode = RequiredMode.REQUIRED)
    private Boolean isFinished = true;

    @Schema(description = "사진", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<StoreImageResponse> images;

    @Schema(description = "해당 가게의 카테고리", requiredMode = RequiredMode.REQUIRED)
    private StoreCategory category;

    @Schema(description = "주소", example = "서울특별시 마포구", requiredMode = RequiredMode.NOT_REQUIRED)
    private String address;

    @Builder
    private UserBookmarkResponse(Long id, Long storeId, String name, int visitCount, int reviewCount, int bookmarkCount,
                                 double averageRating, Integer estimatedWaitingTimeMinutes, List<StoreImageResponse> images,
                                 StoreCategory category, String address
    ) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.visitCount = visitCount;
        this.reviewCount = reviewCount;
        this.bookmarkCount = bookmarkCount;
        this.averageRating = averageRating;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.images = images;
        this.category = category;
        this.address = address;
    }

    public static UserBookmarkResponse of(Bookmark bookmark, Integer estimatedWaitingTime) {
        Store store = bookmark.getStore();
        return UserBookmarkResponse.builder()
                .id(store.getId())
                .storeId(store.getId())
                .name(store.getName())
                .visitCount(store.getVisitCount())
                .reviewCount(store.getReviewCount())
                .bookmarkCount(store.getBookmarkCount())
                .averageRating(store.getAverageRating())
                .estimatedWaitingTimeMinutes(estimatedWaitingTime)
                .images(store.getImages())
                .category(store.getCategory())
                .address(store.getAddress())
                .build();
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
