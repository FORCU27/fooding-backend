package im.fooding.app.service.user.store;

import im.fooding.app.dto.response.user.store.UserStoreInformationResponse;
import im.fooding.core.model.store.information.StoreInformation;
import im.fooding.core.service.store.StoreInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreInformationService {
    private final StoreInformationService storeInformationService;

    @Transactional(readOnly = true)
    public UserStoreInformationResponse retrieve(long storeId) {
        StoreInformation storeInformation = storeInformationService.findByStoreId(storeId);
        return null != storeInformation ? UserStoreInformationResponse.of(storeInformation) : null;
    }
}
