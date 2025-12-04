package im.fooding.realtime.app.controller.pos.waiting;

import im.fooding.realtime.app.dto.response.pos.waiting.PosStoreWaitingEventResponse;
import im.fooding.realtime.app.facade.pos.waiting.PosStoreWaitingSseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v3/pos/stores/{storeId}/waitings")
@Tag(name = "PosStoreWaitingSseController", description = "POS Store Waiting SSE Controller")
public class PosStoreWaitingSseController {

    private final PosStoreWaitingSseService posStoreWaitingSseService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "실시간 웨이팅 정보 업데이트 구독")
    Flux<PosStoreWaitingEventResponse> list(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable long storeId
    ) {
        return posStoreWaitingSseService.subscribe(storeId);
    }
}
