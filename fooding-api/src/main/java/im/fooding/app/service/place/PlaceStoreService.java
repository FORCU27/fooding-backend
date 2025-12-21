package im.fooding.app.service.place;

import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.app.service.user.store.UserStoreService;
import im.fooding.core.global.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceStoreService {

    private final UserStoreService userStoreService;

    public UserStoreResponse retrieve(Long storeId, UserInfo userInfo) {
        return userStoreService.retrieve(storeId, userInfo);
    }
}
