package im.fooding.app.service.user.waiting;

import im.fooding.app.dto.response.user.waiting.UserWaitingAvailableResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserWaitingService {

    private final StoreService storeService;
    private final WaitingSettingService waitingSettingService;

    public UserWaitingAvailableResponse checkAvailability(long storeId) {
        Store store = storeService.findById(storeId);
        WaitingSetting activeSetting = waitingSettingService.getActiveSetting(store);
        boolean isWaitingOpen = activeSetting.isOpen();

        return new UserWaitingAvailableResponse(isWaitingOpen);
    }
}
