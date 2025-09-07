package im.fooding.app.dto.response.admin.store;

import im.fooding.core.dto.request.store.SubwayStationDto;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.store.subway.SubwayStation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AdminStoreResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "점주 id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long ownerId;

    @Schema(description = "가게명", example = "홍가네", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "지역 ID", example = "KR-11", requiredMode = RequiredMode.NOT_REQUIRED)
    private String regionId;

    @Schema(description = "주소", example = "서울특별시 마포구", requiredMode = RequiredMode.REQUIRED)
    private String address;

    @Schema(description = "주소 상세", example = "마포빌딩 2층", requiredMode = RequiredMode.NOT_REQUIRED)
    private String addressDetail;

    @Schema(description = "업종", example = "KOREAN", requiredMode = RequiredMode.REQUIRED)
    private StoreCategory category;

    @Schema(description = "매장소개", example = "설명설명", requiredMode = RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "매장번호", example = "010-0000-0000", requiredMode = RequiredMode.REQUIRED)
    private String contactNumber;

    @Schema(description = "찾아오시는길", example = "홍대입구역 2번출구 앞", requiredMode = RequiredMode.REQUIRED)
    private String direction;

    @Schema(description = "위도", example = "36.40947226931638", requiredMode = RequiredMode.NOT_REQUIRED)
    private Double latitude;

    @Schema(description = "경도", example = "127.12345678901234", requiredMode = RequiredMode.NOT_REQUIRED)
    private Double longitude;

    @Schema(description = "인근 지하철역")
    private List<SubwayStationDto> stations;

    @Schema(description = "가게 상태", example = "APPROVED")
    private StoreStatus status;

    public AdminStoreResponse(Store store) {
        this.id = store.getId();
        this.ownerId = store.getOwner() != null ? store.getOwner().getId() : null;
        this.name = store.getName();
        this.regionId = store.getRegionId();
        this.address = store.getAddress();
        this.addressDetail = store.getAddressDetail();
        this.category = store.getCategory();
        this.description = store.getDescription();
        this.contactNumber = store.getContactNumber();
        this.direction = store.getDirection();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.stations = new ArrayList<>();
        if (store.getSubwayStations() != null) {
            for (SubwayStation station : store.getSubwayStations()) {
                if (station != null) {
                    this.stations.add(SubwayStationDto.of(station));
                }
            }
        }
        this.status = store.getStatus();
    }
}
