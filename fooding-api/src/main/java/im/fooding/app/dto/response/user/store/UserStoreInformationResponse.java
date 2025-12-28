package im.fooding.app.dto.response.user.store;

import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.StoreFacility;
import im.fooding.core.model.store.StorePaymentMethod;
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
public class UserStoreInformationResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private long id;

    @Schema(description = "홈페이지/SNS 링크", example = "[\"https://...\", \"https://...\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<String> links;

    @Schema(description = "시설/서비스 정보", example = "[\"GROUP_USE\", \"TAKEOUT\"]", requiredMode = RequiredMode.REQUIRED)
    private List<StoreFacility> facilities;

    @Schema(description = "결제수단", example = "[\"LOCAL_CURRENCY_CARD\", \"LOCAL_CURRENCY_CASH\"]", requiredMode = RequiredMode.NOT_REQUIRED)
    private List<StorePaymentMethod> paymentMethods;

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
    private UserStoreInformationResponse(long id, List<String> links, List<StoreFacility> facilities, List<StorePaymentMethod> paymentMethods, Boolean parkingAvailable, StoreParkingType parkingType, StoreParkingChargeType parkingChargeType, Integer parkingBasicTimeMinutes, Integer parkingBasicFee, Integer parkingExtraMinutes, Integer parkingExtraFee, Integer parkingMaxDailyFee) {
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

    public static UserStoreInformationResponse of(StoreInformation storeInformation) {
        return UserStoreInformationResponse.builder()
                .id(storeInformation.getId())
                .links(Util.generateStringToList(storeInformation.getLinks()))
                .facilities(Util.generateStringToList(storeInformation.getFacilities()).stream()
                        .map(StoreFacility::valueOf)
                        .toList())
                .paymentMethods(Util.generateStringToList(storeInformation.getPaymentMethods()).stream()
                        .map(StorePaymentMethod::valueOf)
                        .toList())
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
