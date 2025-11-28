package im.fooding.realtime.app.publisher;

import im.fooding.realtime.app.dto.response.pos.reward.GetPosRewardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
// 리워드 생성시 이벤트
public class UpdateRewardListEventPublisher {

    // 다중 사용자가 이벤트를 구독할 수 있게 만들어주는 역할
    private final Sinks.Many<GetPosRewardResponse> sink = Sinks.many()
            .multicast()
            .onBackpressureBuffer();

    // 이벤트 발생 시 호출
    public void publishRewardListUpdate(GetPosRewardResponse response) {
        Sinks.EmitResult result = sink.tryEmitNext(response);

        if (result.isFailure()) log.warn("Failed to emit reward event: {}, response: {}", result, response);
    }

    // SSE 스트림 제공
    public Flux<GetPosRewardResponse> getUpdatedRewardList() {
        return sink.asFlux();
    }
}
