package im.fooding.realtime.app.controller.pos.reward;

import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.realtime.app.dto.request.pos.reward.CreatePosRewardRequest;
import im.fooding.realtime.app.dto.request.pos.reward.GetPosRewardRequest;
import im.fooding.realtime.app.dto.request.pos.reward.UpdateRewardLogRequest;
import im.fooding.realtime.app.dto.response.pos.reward.GetPosRewardResponse;
import im.fooding.realtime.app.facade.pos.reward.PosRewardEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v3/pos/reward")
@Tag( name = "PosRewardController.v3", description = "POS Reward 관련 컨트롤러(SSE)" )
public class PosRewardV3Controller {
    private final PosRewardEventService service;

    // Reward에 변화가 있을 경우 실시간으로 변화를 인식할 통로 연결
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation( summary = "실시간 리워드 로그 정보 업데이트 구독 API" )
    public Flux<GetPosRewardResponse> subscribeRewardEvent() {
        return service.subscribeRewardEvent();
    }

    @GetMapping()
    @Operation( summary = "특정 스토어의 리워드 적립 최초 조회" )
    public Mono<ApiResult<PageResponse<GetPosRewardResponse>>> list(
            @ModelAttribute GetPosRewardRequest request
    ){
        return service.list( request ).map( ApiResult::ok );
    }

    // SSE 필요 -> POS에서 승인/거절 요청이 필요하므로
    @PostMapping("/{storeId}")
    @Operation( summary = "리워드 신규 적립" )
    public Mono<ApiResult<Void>> createReward(
            @RequestBody @Valid CreatePosRewardRequest request,
            @PathVariable Long storeId
    ){
        return service.createReward( request, storeId ).thenReturn( ApiResult.ok() );
    }

    @PatchMapping("/{id}/cancel")
    @Operation( summary = "특정 리워드 로그에 대한 적립 취소" )
    public Mono<ApiResult<Void>> cancelReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
    ){
        return service.cancel( id, request ).thenReturn( ApiResult.ok() );
    }

    @PatchMapping( "/{id}/approve" )
    @Operation( summary = "특정 리워드 로그에 대한 적립 허용" )
    public Mono<ApiResult<Void>> approveReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
    ){
        return service.approve( id, request ).thenReturn( ApiResult.ok() );
    }



}

