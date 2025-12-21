package im.fooding.app.dto.response.user.store;

import im.fooding.core.dto.request.store.SubwayStationDto;
import im.fooding.core.dto.response.StoreImageResponse;
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
public class UserStoreResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "가게명", example = "홍길동 식당", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "주소", example = "서울특별시 마포구", requiredMode = RequiredMode.REQUIRED)
    private String address;

    @Schema(description = "주소 상세", example = "마포빌딩 2층", requiredMode = RequiredMode.REQUIRED)
    private String addressDetail;

    @Schema(description = "업종", example = "KOREAN", requiredMode = RequiredMode.REQUIRED)
    private StoreCategory category;

    @Schema(description = "지역", example = "KR-11", requiredMode = RequiredMode.NOT_REQUIRED)
    private String regionId;

    @Schema(description = "매장소개", example = "설명설명", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "매장번호", example = "010-0000-0000", requiredMode = RequiredMode.REQUIRED)
    private String contactNumber;

    @Schema(description = "찾아오시는길", example = "홍대입구역 2번출구 앞", requiredMode = RequiredMode.REQUIRED)
    private String direction;

    @Schema(description = "해당 가게의 총 방문수", example = "1000", requiredMode = RequiredMode.REQUIRED)
    private int visitCount;

    @Schema(description = "해당 가게의 총 리뷰 개수", example = "246", requiredMode = RequiredMode.REQUIRED)
    private int reviewCount;

    @Schema(description = "해당 가게의 총 관심 수", example = "100", requiredMode = RequiredMode.REQUIRED)
    private int bookmarkCount;

    @Schema(description = "해당 가게의 총 리뷰 평점", example = "4.5", requiredMode = RequiredMode.REQUIRED)
    private double averageRating;

    @Schema(description = "해당 가게의 평균 대기 시간 (웨이팅이 비활성화된 가게일 경우, null 반환)", example = "20", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer estimatedWaitingTimeMinutes;

    @Schema(description = "위도", example = "36.40947226931638", requiredMode = RequiredMode.REQUIRED)
    private Double latitude;

    @Schema(description = "경도", example = "127.12345678901234", requiredMode = RequiredMode.REQUIRED)
    private Double longitude;

    @Schema(description = "영업 종료 여부", example = "false", requiredMode = RequiredMode.REQUIRED)
    private Boolean isFinished = true;

    @Schema(description = "관심 여부", example = "false", requiredMode = RequiredMode.REQUIRED)
    private Boolean isBookmarked = false;

    @Schema(description = "사진", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<StoreImageResponse> images;

    @Schema(description = "인근 지하철역", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<SubwayStationDto> stations;

    @Schema(description = "현재 보고 있는 사람 수", example = "10", requiredMode = RequiredMode.REQUIRED)
    private long viewingCount;

    @Builder
    private UserStoreResponse(Long id, String name, String address, String addressDetail, StoreCategory category, String regionId, String description,
                              String contactNumber, String direction, int visitCount, int reviewCount, int bookmarkCount, double averageRating,
                              Integer estimatedWaitingTimeMinutes, Double latitude, Double longitude, List<StoreImageResponse> images, List<SubwayStationDto> stations, long viewingCount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.addressDetail = addressDetail;
        this.category = category;
        this.regionId = regionId;
        this.description = description;
        this.contactNumber = contactNumber;
        this.direction = direction;
        this.visitCount = visitCount;
        this.reviewCount = reviewCount;
        this.bookmarkCount = bookmarkCount;
        this.averageRating = averageRating;
        this.estimatedWaitingTimeMinutes = estimatedWaitingTimeMinutes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
        this.stations = stations;
        this.viewingCount = viewingCount;
    }

    public static UserStoreResponse of(Store store, Integer estimatedWaitingTime, long viewingCount) {
        List<SubwayStationDto> stations = store.getSubwayStations() != null ?
                store.getSubwayStations().stream().map(it -> SubwayStationDto.of(it)).toList() : null;

        return UserStoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .addressDetail(store.getAddressDetail())
                .category(store.getCategory())
                .regionId(store.getRegionId())
                .description(store.getDescription())
                .contactNumber(store.getContactNumber())
                .direction(store.getDirection())
                .visitCount(store.getVisitCount())
                .reviewCount(store.getReviewCount())
                .bookmarkCount(store.getBookmarkCount())
                .averageRating(store.getAverageRating())
                .estimatedWaitingTimeMinutes(estimatedWaitingTime)
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .images(store.getImages())
                .stations(stations)
                .viewingCount(viewingCount)
                .build();
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
