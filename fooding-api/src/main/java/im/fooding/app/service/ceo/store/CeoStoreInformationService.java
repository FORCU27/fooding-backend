package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.information.CeoCreateStoreInformationRequest;
import im.fooding.app.dto.request.ceo.store.information.CeoUpdateStoreInformationRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreInformationResponse;
import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.information.StoreInformation;
import im.fooding.core.service.store.StoreInformationService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStoreInformationService {
    private final StoreInformationService storeInformationService;
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;

    @Transactional
    public Long create(long storeId, CeoCreateStoreInformationRequest request, long userId) {
        Store store = storeService.findById(storeId);
        checkMember(store.getId(), userId);

        return storeInformationService.create(
                store, Util.generateListToString(request.getLinks()), Util.generateListToString(request.getFacilities()),
                Util.generateListToString(request.getPaymentMethods()), request.getParkingAvailable(), request.getParkingType(), request.getParkingChargeType(),
                request.getParkingBasicTimeMinutes(), request.getParkingBasicFee(), request.getParkingExtraMinutes(),
                request.getParkingExtraFee(), request.getParkingMaxDailyFee()
        ).getId();
    }

    @Transactional
    public void update(long storeId, long id, CeoUpdateStoreInformationRequest request, long userId) {
        Store store = storeService.findById(storeId);
        checkMember(store.getId(), userId);

        storeInformationService.update(
                id, Util.generateListToString(request.getLinks()), Util.generateListToString(request.getFacilities()),
                Util.generateListToString(request.getPaymentMethods()), request.getParkingAvailable(), request.getParkingType(), request.getParkingChargeType(),
                request.getParkingBasicTimeMinutes(), request.getParkingBasicFee(), request.getParkingExtraMinutes(),
                request.getParkingExtraFee(), request.getParkingMaxDailyFee()
        );
    }

    @Transactional(readOnly = true)
    public CeoStoreInformationResponse retrieve(long storeId, long userId) {
        checkMember(storeId, userId);
        StoreInformation storeInformation = storeInformationService.findByStoreId(storeId);
        return null != storeInformation ? CeoStoreInformationResponse.of(storeInformation) : null;
    }

    @Transactional
    public void delete(Long storeId, Long id, long userId) {
        checkMember(storeId, id);
        storeInformationService.delete(id, userId);
    }

    private void checkMember(long storeId, long userId) {
        storeMemberService.checkMember(storeId, userId);
    }
}
