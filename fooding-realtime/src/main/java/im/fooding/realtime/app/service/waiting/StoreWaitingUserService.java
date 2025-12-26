package im.fooding.realtime.app.service.waiting;

import im.fooding.realtime.app.domain.waiting.StoreWaitingUser;
import im.fooding.realtime.app.repository.waiting.StoreWaitingUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StoreWaitingUserService {

    private final StoreWaitingUserRepository storeWaitingUserRepository;

    public Mono<StoreWaitingUser> findById(long id) {
        return storeWaitingUserRepository.findById(id);
    }
}
