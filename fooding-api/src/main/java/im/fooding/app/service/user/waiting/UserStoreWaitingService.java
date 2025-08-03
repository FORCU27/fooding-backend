package im.fooding.app.service.user.waiting;

import im.fooding.app.dto.response.user.waiting.UserStoreWaitingResponse;
import im.fooding.core.service.waiting.StoreWaitingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserStoreWaitingService {

    private final StoreWaitingService storeWaitingService;

    @Transactional(readOnly = true)
    public UserStoreWaitingResponse getStoreWaiting(long id) {
        return UserStoreWaitingResponse.from(storeWaitingService.get(id));
    }
}
