package im.fooding.app.dto.request.ceo.store.information;

import im.fooding.core.model.store.information.StoreParkingChargeType;
import im.fooding.core.model.store.information.StoreParkingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CeoCreateStoreInformationRequest {
    @Schema(description = "홈페이지/SNS 링크", example = "[https://..., https://...]")
    private List<String> links;

    @NotNull
    @Size(min = 1)
    @Schema(description = "시설/서비스 정보", example = "[단체 이용 가능, 포장]")
    private List<String> facilities;

    @Schema(description = "결제수단", example = "[지역화페(카드형), 간편결제]")
    private List<String> paymentMethods;

    @NotNull
    @Schema(description = "주차가능여부", example = "true")
    private Boolean parkingAvailable;

    @Schema(description = "주차 타입 FREE(무료), PAID(유료)", example = "PAID")
    private StoreParkingType parkingType;

    @Schema(description = "주차 과금타입 HOURLY(시간당),FLAT_RATE(정액)", example = "HOURLY")
    private StoreParkingChargeType parkingChargeType;

    @Schema(description = "주차 최초 시간(분단위)", example = "30")
    private Integer parkingBasicTimeMinutes;

    @Schema(description = "주차 최초 금액(0원 무료)", example = "3000")
    private Integer parkingBasicFee;

    @Schema(description = "추가요금 시간(분단위)", example = "10")
    private Integer parkingExtraMinutes;

    @Schema(description = "추가요금 비용", example = "500")
    private Integer parkingExtraFee;

    @Schema(description = "추가요금 최대금액", example = "99999")
    private Integer parkingMaxDailyFee;

    @Builder
    public CeoCreateStoreInformationRequest(List<String> links, List<String> facilities, List<String> paymentMethods, boolean parkingAvailable, StoreParkingType parkingType, StoreParkingChargeType parkingChargeType, Integer parkingBasicTimeMinutes, Integer parkingBasicFee, Integer parkingExtraMinutes, Integer parkingExtraFee, Integer parkingMaxDailyFee) {
        this.links = links;
        this.facilities = facilities;
        this.paymentMethods = paymentMethods;
        this.parkingAvailable = parkingAvailable;
        this.parkingType = parkingType;
        this.parkingChargeType = parkingChargeType;
        this.parkingBasicTimeMinutes = parkingBasicTimeMinutes;
        this.parkingBasicFee = parkingBasicFee;
        this.parkingExtraMinutes = parkingExtraMinutes;
        this.parkingExtraFee = parkingExtraFee;
        this.parkingMaxDailyFee = parkingMaxDailyFee;
    }
}
