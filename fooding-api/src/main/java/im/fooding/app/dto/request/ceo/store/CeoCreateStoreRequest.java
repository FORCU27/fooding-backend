package im.fooding.app.dto.request.ceo.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoCreateStoreRequest {
    @NotBlank(message = "가게 이름은 필수입니다.")
    @Schema(description = "가게명", example = "홍가네")
    private String name;

    @NotNull(message = "가게 지역은 필수입니다.")
    @Schema(description = "가게 지역 ID", example = "KR-11")
    private String regionId;

    @NotBlank(message = "도시는 필수입니다.")
    @Schema(description = "도시", example = "홍대")
    private String city;

    @NotBlank(message = "주소는 필수입니다.")
    @Schema(description = "주소", example = "서울특별시 마포구")
    private String address;

    @NotBlank(message = "카테고리는 필수입니다.")
    @Schema(description = "카테고리", example = "한식")
    private String category;

    @NotBlank(message = "설명은 필수입니다.")
    @Schema(description = "설명", example = "설명설명")
    private String description;

    @NotBlank(message = "가격 카테고리는 필수입니다.")
    @Schema(description = "가격대", example = "15000 ~ 30000")
    private String priceCategory;

    @Schema(description = "이벤트설명", example = "이벤트없음")
    private String eventDescription;

    @NotBlank(message = "연락처는 필수입니다.")
    @Schema(description = "연락처", example = "010-0000-0000")
    private String contactNumber;

    @NotBlank(message = "찾아오는 길은 필수입니다.")
    @Schema(description = "찾아오는 길", example = "홍대입구역 2번출구 앞")
    private String direction;

    @NotBlank(message = "영업 정보는 필수입니다.")
    @Schema(description = "영업 정보", example = "오전9시 ~ 오후9시")
    private String information;

    @NotNull(message = "주차 가능 여부는 필수입니다.")
    @Schema(description = "주차가능여부", example = "true")
    private Boolean isParkingAvailable;

    @NotNull(message = "신규 오픈 여부는 필수입니다.")
    @Schema(description = "신규오픈여부", example = "true")
    private Boolean isNewOpen;

    @NotNull(message = "포장 가능 여부는 필수입니다.")
    @Schema(description = "포장가능여부", example = "true")
    private Boolean isTakeOut;

    @Schema(description = "위도", example = "36.40947226931638")
    private Double latitude;

    @Schema(description = "경도", example = "127.12345678901234")
    private Double longitude;
}
