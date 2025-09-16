package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserStoreSearchResponse {
    @Schema(description = "id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "업종", example = "KOREAN", requiredMode = Schema.RequiredMode.REQUIRED)
    private StoreCategory category;

    @Schema(description = "지역ID", example = "KR-11110101", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String regionId;

    @Schema(description = "가게명", example = "홍길동 식당", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "주소", example = "경기 안양시 만안구 안양로", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @Schema(description = "해당 가게의 총 방문수", example = "1000", requiredMode = Schema.RequiredMode.REQUIRED)
    private int visitCount;

    @Schema(description = "해당 가게의 총 리뷰 개수", example = "246", requiredMode = Schema.RequiredMode.REQUIRED)
    private int reviewCount;

    @Schema(description = "해당 가게의 총 단골수", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private int bookmarkCount;

    @Schema(description = "해당 가게의 총 리뷰 평점", example = "4.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private double averageRating;

    @Schema(description = "해당 가게의 평균 대기 시간 (웨이팅이 비활성화된 가게일 경우, null 반환)", example = "20", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer estimatedWaitingTimeMinutes;

    @Schema(description = "영업 종료 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isFinished = true;

    @Schema(description = "관심 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isBookmarked = false;

    @Schema(description = "사진", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<UserStoreImageResponse> images;

    @Builder
    private UserStoreSearchResponse(Long id, StoreCategory category, String regionId, String name, String address, int visitCount,
                                    int reviewCount, int bookmarkCount, double averageRating, Integer estimatedWaitingTimeMinutes,
                                    List<UserStoreImageResponse> images) {
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

    public static UserStoreSearchResponse of(Store store, Integer estimatedWaitingTime) {
        List<UserStoreImageResponse> images = store.getImages() != null ? store.getImages().stream()
                .sorted(Comparator.comparing(StoreImage::getSortOrder).thenComparing(Comparator.comparing(StoreImage::getId).reversed()))
                .map(UserStoreImageResponse::of)
                .toList() : null;

        return UserStoreSearchResponse.builder()
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
                .images(images)
                .build();
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
