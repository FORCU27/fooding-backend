package im.fooding.realtime.app.facade.pos.waiting;

import im.fooding.realtime.app.dto.response.pos.waiting.PosStoreWaitingEventResponse;
import im.fooding.realtime.global.infra.StoreWaitingEventHub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class PosStoreWaitingSseService {

    private final StoreWaitingEventHub storeWaitingEventHub;

    public Flux<PosStoreWaitingEventResponse> subscribe(Long storeId) {
        return storeWaitingEventHub.subscribe(storeId)
            .flatMap(event -> Flux.just(PosStoreWaitingEventResponse.from(event)));
    }
}
