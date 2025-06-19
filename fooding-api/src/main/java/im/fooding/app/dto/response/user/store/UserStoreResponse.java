package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserStoreResponse {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "가게명", example = "홍길동 식당")
    private String name;

    @Schema(description = "가게가 위치한 도시", example = "합정")
    private String city;

    @Schema(description = "주소", example = "서울특별시 마포구")
    private String address;

    @Schema(description = "카테고리", example = "한식")
    private String category;

    @Schema(description = "설명", example = "설명설명")
    private String description;

    @Schema(description = "가격대", example = "15000 ~ 30000")
    private String priceCategory;

    @Schema(description = "이벤트설명", example = "이벤트없음")
    private String eventDescription;

    @Schema(description = "연락처", example = "010-0000-0000")
    private String contactNumber;

    @Schema(description = "오시는길", example = "홍대입구역 2번출구 앞")
    private String direction;

    @Schema(description = "해당 가게의 총 방문수", example = "1000")
    private int visitCount;

    @Schema(description = "해당 가게의 총 리뷰 개수", example = "246")
    private int reviewCount;

    @Schema(description = "해당 가게의 총 리뷰 평점", example = "4.5")
    private double averageRating;

    @Schema(description = "주차가능여부", example = "true")
    private Boolean isParkingAvailable;

    @Schema(description = "신규오픈여부", example = "true")
    private Boolean isNewOpen;

    @Schema(description = "포장가능여부", example = "true")
    private Boolean isTakeOut;

    @Schema(description = "해당 가게의 평균 대기 시간 (웨이팅이 비활성화된 가게일 경우, null 반환)", example = "20")
    private Integer estimatedWaitingTimeMinutes;

    @Schema(description = "위도", example = "36.40947226931638")
    private Double latitude;

    @Schema(description = "경도", example = "127.12345678901234")
    private Double longitude;

    @Schema(description = "사진")
    private List<UserStoreImageResponse> images;

    @Builder
    private UserStoreResponse(Long id, String name, String city, String address, String category, String description,
                              String priceCategory, String eventDescription, String contactNumber, String direction, int visitCount,
                              int reviewCount, double averageRating, Boolean isParkingAvailable, Boolean isNewOpen, Boolean isTakeOut,
                              Integer estimatedWaitingTimeMinutes, Double latitude, Double longitude, List<UserStoreImageResponse> images) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
        this.category = category;
        this.description = description;
        this.priceCategory = priceCategory;
        this.eventDescription = eventDescription;
        this.contactNumber = contactNumber;
        this.direction = direction;
        this.visitCount = visitCount;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.isParkingAvailable = isParkingAvailable;
        this.isNewOpen = isNewOpen;
        this.isTakeOut = isTakeOut;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
    }

    public static UserStoreResponse of(Store store, Integer estimatedWaitingTime) {
        List<UserStoreImageResponse> images = store.getImages() != null ? store.getImages().stream()
                .sorted(Comparator.comparing(StoreImage::getSortOrder).thenComparing(Comparator.comparing(StoreImage::getId).reversed()))
                .map(UserStoreImageResponse::of)
                .toList() : null;

        return UserStoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .city(store.getCity())
                .address(store.getAddress())
                .category(store.getCategory())
                .description(store.getDescription())
                .priceCategory(store.getPriceCategory())
                .eventDescription(store.getEventDescription())
                .contactNumber(store.getContactNumber())
                .direction(store.getDirection())
                .visitCount(store.getVisitCount())
                .reviewCount(store.getReviewCount())
                .averageRating(store.getAverageRating())
                .isParkingAvailable(store.isParkingAvailable())
                .isNewOpen(store.isNewOpen())
                .isTakeOut(store.isTakeOut())
                .estimatedWaitingTimeMinutes(estimatedWaitingTime)
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .images(images)
                .build();
    }
}
