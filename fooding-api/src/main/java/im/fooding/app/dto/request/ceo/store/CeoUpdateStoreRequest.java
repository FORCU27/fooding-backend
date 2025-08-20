package im.fooding.app.dto.request.ceo.store;

import im.fooding.core.model.store.StoreCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoUpdateStoreRequest {
    @NotBlank(message = "가게 이름은 필수입니다.")
    @Schema(description = "가게명", example = "홍가네")
    private String name;

    @Schema(description = "가게 지역 ID", example = "KR-11")
    private String regionId;

    @NotBlank(message = "주소는 필수입니다.")
    @Schema(description = "주소", example = "서울특별시 마포구")
    private String address;

    @Schema(description = "주소 상세", example = "마포상가 2층")
    private String addressDetail;

    @NotNull(message = "업종 필수입니다.")
    @Schema(description = "업종", example = "KOREAN")
    private StoreCategory category;

    @NotBlank(message = "매장소개는 필수입니다.")
    @Schema(description = "매장소개", example = "설명설명")
    private String description;

    @NotBlank(message = "매장번호는 필수입니다.")
    @Schema(description = "매장번호", example = "010-0000-0000")
    private String contactNumber;

    @NotBlank(message = "찾아오시는 길은 필수입니다.")
    @Schema(description = "찾아오시는 길", example = "홍대입구역 2번출구 앞")
    private String direction;

    @NotNull(message = "위도 값은 필수입니다.")
    @Schema(description = "위도", example = "36.40947226931638")
    private Double latitude;

    @NotNull(message = "경도 값은 필수입니다.")
    @Schema(description = "경도", example = "127.12345678901234")
    private Double longitude;
}
