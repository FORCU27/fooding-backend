package im.fooding.app.dto.response.admin.store;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import im.fooding.core.dto.request.store.SubwayStationDto;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.subway.SubwayStation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class AdminStoreResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private final Long id;

    @Schema(description = "점주 id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private final Long ownerId;

    @Schema(description = "가게명", example = "홍가네", requiredMode = RequiredMode.REQUIRED)
    private final String name;

    @Schema(description = "지역 ID", example = "KR-11", requiredMode = RequiredMode.REQUIRED)
    private final String regionId;

    @Schema(description = "도시", example = "홍대", requiredMode = RequiredMode.REQUIRED)
    private final String city;

    @Schema(description = "주소", example = "서울특별시 마포구", requiredMode = RequiredMode.REQUIRED)
    private final String address;

    @Schema(description = "카테고리", example = "한식", requiredMode = RequiredMode.REQUIRED)
    private final String category;

    @Schema(description = "설명", example = "설명설명", requiredMode = RequiredMode.REQUIRED)
    private final String description;

    @Schema(description = "가격대", example = "15000 ~ 30000", requiredMode = RequiredMode.REQUIRED)
    private final String priceCategory;

    @Schema(description = "이벤트설명", example = "이벤트없음", requiredMode = RequiredMode.NOT_REQUIRED)
    private final String eventDescription;

    @Schema(description = "연락처", example = "010-0000-0000", requiredMode = RequiredMode.REQUIRED)
    private final String contactNumber;

    @Schema(description = "오시는길", example = "홍대입구역 2번출구 앞", requiredMode = RequiredMode.REQUIRED)
    private final String direction;

    @Schema(description = "영업 정보", example = "오전9시 ~ 오후9시", requiredMode = RequiredMode.REQUIRED)
    private final String information;

    @JsonProperty("isParkingAvailable")
    @Schema(description = "주차가능여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private final boolean isParkingAvailable;

    @JsonProperty("isNewOpen")
    @Schema(description = "신규오픈여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private final boolean isNewOpen;

    @JsonProperty("isTakeOut")
    @Schema(description = "포장가능여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private final boolean isTakeOut;

    @Schema(description = "위도", example = "36.40947226931638", requiredMode = RequiredMode.NOT_REQUIRED)
    private final Double latitude;

    @Schema(description = "경도", example = "127.12345678901234", requiredMode = RequiredMode.NOT_REQUIRED)
    private final Double longitude;

    @Schema(description = "인근 지하철역")
    private List<SubwayStationDto> stations;

    @JsonCreator
    public AdminStoreResponse(Store store) {
        this.id = store.getId();
        this.ownerId = store.getOwner() != null ? store.getOwner().getId() : null;
        this.name = store.getName();
        this.regionId = store.getRegion().getId();
        this.city = store.getCity();
        this.address = store.getAddress();
        this.category = store.getCategory();
        this.description = store.getDescription();
        this.priceCategory = store.getPriceCategory();
        this.eventDescription = store.getEventDescription();
        this.contactNumber = store.getContactNumber();
        this.direction = store.getDirection();
        this.information = store.getInformation();
        this.isParkingAvailable = store.isParkingAvailable();
        this.isNewOpen = store.isNewOpen();
        this.isTakeOut = store.isTakeOut();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.stations = store.getSubwayStations() != null ?
                store.getSubwayStations().stream()
                        .filter(Objects::nonNull)
                        .map(SubwayStationDto::of)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }
}
