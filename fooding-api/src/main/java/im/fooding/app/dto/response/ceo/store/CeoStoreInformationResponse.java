package im.fooding.app.dto.response.ceo.store;

import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.information.StoreInformation;
import im.fooding.core.model.store.information.StoreParkingChargeType;
import im.fooding.core.model.store.information.StoreParkingType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CeoStoreInformationResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "홈페이지/SNS 링크", example = "[\"https://...\", \"https://...\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<String> links;

    @Schema(description = "시설/서비스 정보", example = "[\"단체 이용 가능\", \"포장\"]", requiredMode = RequiredMode.REQUIRED)
    private List<String> facilities;

    @Schema(description = "결제수단", example = "[\"지역화페(카드형)\", \"간편결제\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<String> paymentMethods;

    @Schema(description = "주차가능여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private Boolean parkingAvailable;

    @Schema(description = "주차 타입 FREE(무료), PAID(유료)", example = "PAID", requiredMode = RequiredMode.NOT_REQUIRED)
    private StoreParkingType parkingType;

    @Schema(description = "주차 과금타입 HOURLY(시간당),FLAT_RATE(정액)", example = "HOURLY", requiredMode = RequiredMode.NOT_REQUIRED)
    private StoreParkingChargeType parkingChargeType;

    @Schema(description = "주차 최초 시간(분단위)", example = "30", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer parkingBasicTimeMinutes;

    @Schema(description = "주차 최초 금액(0원 무료)", example = "3000", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer parkingBasicFee;

    @Schema(description = "추가요금 시간(분단위)", example = "10", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer parkingExtraMinutes;

    @Schema(description = "추가요금 비용", example = "500", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer parkingExtraFee;

    @Schema(description = "추가요금 최대금액", example = "99999", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer parkingMaxDailyFee;

    @Builder
    private CeoStoreInformationResponse(long id, List<String> links, List<String> facilities, List<String> paymentMethods, Boolean parkingAvailable, StoreParkingType parkingType, StoreParkingChargeType parkingChargeType, Integer parkingBasicTimeMinutes, Integer parkingBasicFee, Integer parkingExtraMinutes, Integer parkingExtraFee, Integer parkingMaxDailyFee) {
        this.id = id;
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

    public static CeoStoreInformationResponse of(StoreInformation storeInformation) {
        return CeoStoreInformationResponse.builder()
                .id(storeInformation.getId())
                .links(Util.generateStringToList(storeInformation.getLinks()))
                .facilities(Util.generateStringToList(storeInformation.getFacilities()))
                .paymentMethods(Util.generateStringToList(storeInformation.getPaymentMethods()))
                .parkingAvailable(storeInformation.isParkingAvailable())
                .parkingType(storeInformation.getParkingType())
                .parkingChargeType(storeInformation.getParkingChargeType())
                .parkingBasicTimeMinutes(storeInformation.getParkingBasicTimeMinutes())
                .parkingBasicFee(storeInformation.getParkingBasicFee())
                .parkingExtraMinutes(storeInformation.getParkingExtraMinutes())
                .parkingExtraFee(storeInformation.getParkingExtraFee())
                .parkingMaxDailyFee(storeInformation.getParkingMaxDailyFee())
                .build();
    }
}
