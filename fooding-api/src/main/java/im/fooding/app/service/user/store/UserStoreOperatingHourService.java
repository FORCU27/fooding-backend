package im.fooding.app.service.user.store;

import im.fooding.app.dto.response.user.store.UserStoreOperatingHourResponse;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.service.store.StoreOperatingHourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreOperatingHourService {
    private final StoreOperatingHourService storeOperatingHourService;

    @Transactional(readOnly = true)
    public UserStoreOperatingHourResponse retrieve(long storeId) {
        StoreOperatingHour storeOperatingHour = storeOperatingHourService.findByStoreId(storeId);
        return null != storeOperatingHour ? UserStoreOperatingHourResponse.of(storeOperatingHour) : null;
    }
}
