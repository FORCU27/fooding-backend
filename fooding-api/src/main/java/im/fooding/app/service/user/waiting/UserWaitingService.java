package im.fooding.app.service.user.waiting;

import im.fooding.app.dto.response.user.waiting.UserWaitingAvailableResponse;
import im.fooding.app.dto.response.user.waiting.UserWaitingServiceUsageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreServiceType;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.StoreServiceService;
import im.fooding.core.service.waiting.WaitingSettingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserWaitingService {

    private final StoreService storeService;
    private final StoreServiceService storeServiceService;
    private final WaitingSettingService waitingSettingService;

    public UserWaitingServiceUsageResponse checkServiceUsage(long storeId) {
        var storeServices = storeServiceService.findByStoreId(storeId);

        boolean usage = storeServices.stream()
                .anyMatch(storeService ->
                        storeService.getType() == StoreServiceType.WAITING && storeService.isActivation()
                );

        return new UserWaitingServiceUsageResponse(usage);
    }

    public UserWaitingAvailableResponse checkAvailability(long storeId) {
        Store store = storeService.findById(storeId);
        return waitingSettingService.findActiveSetting(store)
                .map(activeSetting -> new UserWaitingAvailableResponse(activeSetting.isOpen()))
                .orElse(new UserWaitingAvailableResponse(false));
    }
}
