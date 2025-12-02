package im.fooding.realtime.app.facade.pos.waiting;

import im.fooding.realtime.app.dto.request.pos.waiting.PosStoreWaitingListRequest;
import im.fooding.realtime.app.dto.response.pos.waiting.PosStoreWaitingResponse;
import im.fooding.realtime.app.service.waiting.StoreWaitingService;
import im.fooding.realtime.app.service.waiting.StoreWaitingUserService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PosStoreWaitingService {

    private final StoreWaitingService storeWaitingService;
    private final StoreWaitingUserService storeWaitingUserService;

    public Mono<Page<PosStoreWaitingResponse>> list(long storeId, PosStoreWaitingListRequest request) {
        return storeWaitingService.listByStoreId(storeId, request.getStatus(), request.getPageable())
                .flatMap(page -> {
                    List<Mono<PosStoreWaitingResponse>> userMonos = page.getContent().stream()
                            .map(storeWaiting -> {
                                Long waitingUserId = storeWaiting.getWaitingUserId();
                                if (waitingUserId == null) {
                                    return Mono.just(PosStoreWaitingResponse.from(storeWaiting, null));
                                }
                                return storeWaitingUserService.findById(waitingUserId)
                                        .map(user -> PosStoreWaitingResponse.from(storeWaiting, user));
                            })
                            .toList();

                    return Mono.zip(userMonos, objects -> {
                        List<PosStoreWaitingResponse> responses = Arrays.stream(objects)
                                .map(obj -> (PosStoreWaitingResponse) obj)
                                .toList();
                        return new PageImpl<>(responses, request.getPageable(), page.getTotalElements());
                    });
                });
    }
}
