package im.fooding.app.dto.response.user.store;

import im.fooding.core.dto.response.StoreImageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.popular.PopularStore;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserStoreListResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "업종", example = "KOREAN", requiredMode = RequiredMode.REQUIRED)
    private StoreCategory category;

    @Schema(description = "지역코드", example = "KR-11", requiredMode = RequiredMode.NOT_REQUIRED)
    private String regionId;

    @Schema(description = "가게명", example = "홍길동 식당", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "주소", example = "서울특별시 마포구", requiredMode = RequiredMode.REQUIRED)
    private String address;

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

    @Schema(description = "관심 여부", example = "false", requiredMode = RequiredMode.REQUIRED)
    private Boolean isBookmarked = false;

    @Schema(description = "사진", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<StoreImageResponse> images;

    @Builder
    private UserStoreListResponse(Long id, StoreCategory category, String regionId, String name, String address, double averageRating, int visitCount,
                                  int reviewCount, int bookmarkCount, Integer estimatedWaitingTimeMinutes, List<StoreImageResponse> images) {
        this.id = id;
        this.category = category;
        this.regionId = regionId;
        this.name = name;
        this.address = address;
        this.visitCount = visitCount;
        this.reviewCount = reviewCount;
        this.bookmarkCount = bookmarkCount;
        this.averageRating = averageRating;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.images = images;
    }

    public static UserStoreListResponse of(Store store, Integer estimatedWaitingTime) {
        return UserStoreListResponse.builder()
                .id(store.getId())
                .category(store.getCategory())
                .regionId(store.getRegionId())
                .name(store.getName())
                .address(store.getAddress())
                .visitCount(store.getVisitCount())
                .reviewCount(store.getReviewCount())
                .bookmarkCount(store.getBookmarkCount())
                .averageRating(store.getAverageRating())
                .estimatedWaitingTimeMinutes(estimatedWaitingTime)
                .images(store.getImages())
                .build();
    }

    public static UserStoreListResponse of(PopularStore store, Integer estimatedWaitingTime) {
        return UserStoreListResponse.builder()
                .id(store.id())
                .category(store.category())
                .regionId(store.regionId())
                .name(store.name())
                .address(store.address())
                .visitCount(store.visitCount())
                .reviewCount(store.reviewCount())
                .bookmarkCount(store.bookmarkCount())
                .averageRating(store.averageRating())
                .estimatedWaitingTimeMinutes(estimatedWaitingTime)
                .images(store.images())
                .build();
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
