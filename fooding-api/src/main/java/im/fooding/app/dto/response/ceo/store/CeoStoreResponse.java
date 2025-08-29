package im.fooding.app.dto.response.ceo.store;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.subway.SubwayStation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;

import java.util.List;

@Getter
public class CeoStoreResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private final Long id;

    @Schema(description = "점주 id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private final Long ownerId;

    @Schema(description = "가게명", example = "홍가네", requiredMode = RequiredMode.REQUIRED)
    private final String name;

    @Schema(description = "지역 ID", example = "KR-11", requiredMode = RequiredMode.NOT_REQUIRED)
    private final String regionId;

    @Schema(description = "주소", example = "서울특별시 마포구", requiredMode = RequiredMode.REQUIRED)
    private final String address;

    @Schema(description = "주소 상세", example = "마포빌딩 2층", requiredMode = RequiredMode.NOT_REQUIRED)
    private final String addressDetail;

    @Schema(description = "업종", example = "KOREAN", requiredMode = RequiredMode.REQUIRED)
    private final StoreCategory category;

    @Schema(description = "매장소개", example = "설명설명", requiredMode = RequiredMode.REQUIRED)
    private final String description;

    @Schema(description = "매장번호", example = "010-0000-0000", requiredMode = RequiredMode.REQUIRED)
    private final String contactNumber;

    @Schema(description = "찾아오시는길", example = "홍대입구역 2번출구 앞", requiredMode = RequiredMode.REQUIRED)
    private final String direction;

    @Schema(description = "위도", example = "36.40947226931638", requiredMode = RequiredMode.REQUIRED)
    private final Double latitude;

    @Schema(description = "경도", example = "127.12345678901234", requiredMode = RequiredMode.REQUIRED)
    private final Double longitude;

    @Schema(description = "해당 가게의 총 방문수", example = "1000", requiredMode = RequiredMode.REQUIRED)
    private final int visitCount;

    @Schema(description = "해당 가게의 총 리뷰 개수", example = "246", requiredMode = RequiredMode.REQUIRED)
    private final int reviewCount;

    @Schema(description = "해당 가게의 총 관심 수", example = "100", requiredMode = RequiredMode.REQUIRED)
    private final int bookmarkCount;

    @Schema(description = "인근 지하철역", example = "홍대입구역 2호선", requiredMode = RequiredMode.REQUIRED)
    private final List<SubwayStation> stations;

    public CeoStoreResponse(Store store) {
        this.id = store.getId();
        this.ownerId = store.getOwner() != null ? store.getOwner().getId() : null;
        this.regionId = store.getRegionId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.addressDetail = store.getAddressDetail();
        this.category = store.getCategory();
        this.description = store.getDescription();
        this.contactNumber = store.getContactNumber();
        this.direction = store.getDirection();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.visitCount = store.getVisitCount();
        this.reviewCount = store.getReviewCount();
        this.bookmarkCount = store.getBookmarkCount();
        this.stations = store.getSubwayStations();
    }
}
