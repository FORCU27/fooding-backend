package im.fooding.app.controller.user.reward;

import im.fooding.app.dto.request.user.reward.GetRewardLogRequest;
import im.fooding.app.dto.request.user.reward.GetRewardPointRequest;
import im.fooding.app.dto.request.user.reward.GetUserRewardLogRequest;
import im.fooding.app.dto.response.user.reward.GetRewardLogResponse;
import im.fooding.app.dto.response.user.reward.GetRewardPointResponse;
import im.fooding.app.service.user.reward.RewardApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.model.reward.RewardStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/reward")
@Tag( name = "UserRewardController", description = "User들이 사용하는 리워드 관련 컨트롤러" )
public class UserRewardController {
    private final RewardApplicationService service;

    @GetMapping( "/logs" )
    @Operation( summary = "스토어 별 보유 포인트 적립 내역 조회" )
    public ApiResult<PageResponse<GetRewardLogResponse>> getRewardLog(
            @ModelAttribute GetRewardLogRequest request
    ){
        Page<GetRewardLogResponse> result = service.getRewardLog( request );
        return ApiResult.ok( PageResponse.of( result.stream().toList(), PageInfo.of( result ) )) ;
    }

    @GetMapping("/store")
    @Operation( summary = "스토어 별 누적 보유 포인트 조회" )
    public ApiResult<PageResponse<GetRewardPointResponse>> getRewardPoint(
            @ModelAttribute GetRewardPointRequest request
    ){
        Page<GetRewardPointResponse> result = service.getRewardPoint( request );
        return ApiResult.ok( PageResponse.of( result.stream().toList(), PageInfo.of( result ) ) );
    }

    @GetMapping("/store/{storeId}/used")
    @Operation( summary = "스토어 별 포인트 사용 횟수 조회" )
    public ApiResult<Long> getStoreUsedPoint(
            @PathVariable long storeId,
            @AuthenticationPrincipal UserInfo info
    ){
        return ApiResult.ok( service.getLogCount( storeId, info.getId(), RewardStatus.USED ) );
    }

    @GetMapping("/store/{storeId}/earned")
    @Operation( summary = "스토어 별 포인트 적립 횟수 조회" )
    public ApiResult<Long> getStoreEarnedPoint(
            @PathVariable long storeId,
            @AuthenticationPrincipal UserInfo info
    ){
        return ApiResult.ok( service.getLogCount( storeId, info.getId(), RewardStatus.EARNED ) );
    }

    @GetMapping("/store/{storeId}/canceled")
    @Operation( summary = "스토어 별 포인트 적립 취소 횟수 조회" )
    public ApiResult<Long> getStoreCancelPoint(
            @PathVariable long storeId,
            @AuthenticationPrincipal UserInfo info
    ){
        return ApiResult.ok( service.getLogCount( storeId, info.getId(), RewardStatus.CANCELED ) );
    }

    @GetMapping("/logs/personal")
    @Operation( summary = "사용자 별 리워드 로그 조회" )
    public ApiResult<PageResponse<GetRewardLogResponse>> getUserRewardLog(
            @AuthenticationPrincipal UserInfo userInfo,
            @ModelAttribute GetUserRewardLogRequest request
    ){
        long id = userInfo.getId();
        return ApiResult.ok( service.getUserRewardLog( id, request ) );
    }

}
