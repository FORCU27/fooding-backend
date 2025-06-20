package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.information.StoreInformation;
import im.fooding.core.model.store.information.StoreParkingChargeType;
import im.fooding.core.model.store.information.StoreParkingType;
import im.fooding.core.repository.store.information.StoreInformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreInformationService {
    private final StoreInformationRepository repository;

    public StoreInformation create(Store store, String links, String facilities, String paymentMethods, boolean parkingAvailable,
                       StoreParkingType parkingType, StoreParkingChargeType parkingChargeType, Integer parkingBasicTimeMinutes,
                       Integer parkingBasicFee, Integer parkingExtraMinutes, Integer parkingExtraFee, Integer parkingMaxDailyFee) {
        checkDuplicate(store.getId());
        StoreInformation storeInformation = StoreInformation.builder()
                .store(store)
                .links(links)
                .facilities(facilities)
                .paymentMethods(paymentMethods)
                .parkingAvailable(parkingAvailable)
                .parkingType(parkingType)
                .parkingChargeType(parkingChargeType)
                .parkingBasicTimeMinutes(parkingBasicTimeMinutes)
                .parkingBasicFee(parkingBasicFee)
                .parkingExtraMinutes(parkingExtraMinutes)
                .parkingExtraFee(parkingExtraFee)
                .parkingMaxDailyFee(parkingMaxDailyFee)
                .build();
        return repository.save(storeInformation);
    }

    public void update(long id, String links, String facilities, String paymentMethods, boolean parkingAvailable, StoreParkingType parkingType,
                       StoreParkingChargeType parkingChargeType, Integer parkingBasicTimeMinutes, Integer parkingBasicFee,
                       Integer parkingExtraMinutes, Integer parkingExtraFee, Integer parkingMaxDailyFee) {
        StoreInformation storeInformation = findById(id);
        storeInformation.update(links, facilities, paymentMethods, parkingAvailable, parkingType, parkingChargeType,
                parkingBasicTimeMinutes, parkingBasicFee, parkingExtraMinutes, parkingExtraFee, parkingMaxDailyFee);
    }

    public StoreInformation findById(long id) {
        return repository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_INFORMATION_NOT_FOUND));
    }

    public StoreInformation findByStoreId(long storeId) {
        return repository.findByStoreIdAndDeletedIsFalse(storeId).orElse(null);
    }

    private void checkDuplicate(long storeId) {
        if (repository.existsByStoreIdAndDeletedIsFalse(storeId)) {
            throw new ApiException(ErrorCode.STORE_INFORMATION_DUPLICATED);
        }
    }

    public void delete(Long id, long deletedBy) {
        StoreInformation storeInformation = findById(id);
        storeInformation.delete(deletedBy);
    }
}
