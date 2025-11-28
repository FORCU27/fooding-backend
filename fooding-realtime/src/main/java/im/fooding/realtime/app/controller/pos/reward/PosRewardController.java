package im.fooding.realtime.app.controller.pos.reward;

import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.realtime.app.dto.request.pos.reward.GetPosRewardRequest;
import im.fooding.realtime.app.dto.request.pos.reward.UpdateRewardLogRequest;
import im.fooding.realtime.app.dto.response.pos.reward.GetPosRewardResponse;
import im.fooding.realtime.app.facade.pos.reward.PosRewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v2/pos/reward")
@Tag( name = "PosRewardController.v2", description = "POS Reward 관련 컨트롤러" )
public class PosRewardController {
    private final PosRewardService service;

    @GetMapping()
    @Operation( summary = "특정 스토어의 리워드 적립 조회" )
    public Mono<ApiResult<PageResponse<GetPosRewardResponse>>> list(
            @ModelAttribute GetPosRewardRequest request
    ){
        return service.list( request ).map( ApiResult::ok );
    }

    @PatchMapping("/{id}/cancel")
    @Operation( summary = "특정 리워드 로그에 대한 적립 취소" )
    public Mono<ApiResult<Void>> cancelReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
    ){
        return service.cancel( id, request ).then( Mono.fromSupplier(ApiResult::ok));
    }

    @PatchMapping( "/{id}/approve" )
    @Operation( summary = "특정 리워드 로그에 대한 적립 허용" )
    public Mono<ApiResult<Void>> approveReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
    ){
        return service.approve( id, request ).then( Mono.fromSupplier(ApiResult::ok) );
    }

    @PatchMapping( "/{id}/memo" )
    @Operation( summary = "특정 리워드 로그에 메모 변경" )
    public Mono<ApiResult<Void>> updateMemo(
            @PathVariable Long id,
            @RequestBody String memo
    ){
        return service.updateMemo( id, memo ).then( Mono.fromSupplier(ApiResult::ok) );
    }
}
