package im.fooding.core.model.store.information;

import im.fooding.core.model.BaseEntity;
import im.fooding.core.model.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class StoreInformation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, unique = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Store store;

    private String links;

    private String facilities;

    private String paymentMethods;

    private boolean parkingAvailable; // 주차 가능 여부

    @Enumerated(EnumType.STRING)
    private StoreParkingType parkingType; // 주차 가능시 유료, 무료

    @Enumerated(EnumType.STRING)
    private StoreParkingChargeType parkingChargeType; // 시간당과금, 정액과금

    private Integer parkingBasicTimeMinutes; // 최초 기본시간

    private Integer parkingBasicFee; // 최초 기본시간 금액

    private Integer parkingExtraMinutes; //추가요금 기준시간

    private Integer parkingExtraFee;// 추가요금비

    private Integer parkingMaxDailyFee; //최대 요금

    @Builder
    public StoreInformation(Store store, String links, String facilities, String paymentMethods, boolean parkingAvailable, StoreParkingType parkingType, StoreParkingChargeType parkingChargeType, Integer parkingBasicTimeMinutes, Integer parkingBasicFee, Integer parkingExtraMinutes, Integer parkingExtraFee, Integer parkingMaxDailyFee) {
        this.store = store;
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

    public void update(String links, String facilities, String paymentMethods, boolean parkingAvailable, StoreParkingType parkingType, StoreParkingChargeType parkingChargeType, Integer parkingBasicTimeMinutes, Integer parkingBasicFee, Integer parkingExtraMinutes, Integer parkingExtraFee, Integer parkingMaxDailyFee) {
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
