package im.fooding.app.controller.pos.reward;

import im.fooding.app.dto.request.pos.reward.GetPosRewardRequest;
import im.fooding.app.dto.request.pos.reward.UpdateRewardLogRequest;
import im.fooding.app.dto.response.pos.reward.GetPosRewardResponse;
import im.fooding.app.service.pos.reward.PosRewardService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pos/reward")
@Tag( name = "PosRewardController", description = "[POS] Reward 관련 컨트롤러" )
public class PosRewardController {
    private final PosRewardService service;

    @GetMapping()
    @Operation( summary = "특정 스토어의 리워드 적립 조회" )
    public ApiResult<PageResponse<GetPosRewardResponse>> list(
            @ModelAttribute GetPosRewardRequest request
    ){
        return ApiResult.ok( service.list( request ) );
    }

    @PatchMapping("/{id}/cancel")
    @Operation( summary = "특정 리워드 로그에 대한 적립 취소" )
    public ApiResult<Void> cancelReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
            ){
        service.cancel( id, request );
        return ApiResult.ok();
    }

    @PatchMapping( "/{id}/approve" )
    @Operation( summary = "특정 리워드 로그에 대한 적립 허용" )
    public ApiResult<Void> approveReward(
            @PathVariable Long id,
            @RequestBody UpdateRewardLogRequest request
    ){
        service.approve( id, request );
        return ApiResult.ok();
    }

    @PatchMapping( "/{id}/memo" )
    @Operation( summary = "특정 리워드 로그에 메모 변경" )
    public ApiResult<Void> updateMemo(
            @PathVariable Long id,
            @RequestBody String memo
    ){
        service.updateMemo( id, memo );
        return ApiResult.ok();
    }

}
