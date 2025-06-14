package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStoreResponse {
    @Schema(description = "id", example = "1")
    private Long id;

    @Schema(description = "가게명", example = "홍길동 식당")
    private String name;

    @Schema(description = "가게 이미지 URL", example = "https://example.com/store.jpg")
    private String mainImage;

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

    @Builder
    private UserStoreResponse(
            Long id, String name, String mainImage, String city, String address, String category, String description,
            String priceCategory, String eventDescription, String contactNumber, String direction, int visitCount,
            int reviewCount, double averageRating, Boolean isParkingAvailable, Boolean isNewOpen, Boolean isTakeOut,
            Integer estimatedWaitingTimeMinutes
    ) {
        this.id = id;
        this.name = name;
        this.mainImage = mainImage;
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
    }

    public static UserStoreResponse of(Store store, Integer estimatedWaitingTime) {
        return UserStoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .mainImage(null)
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
                .build();
    }
}
