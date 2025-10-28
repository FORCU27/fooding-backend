package im.fooding.app.controller.ceo;

import im.fooding.app.dto.response.ceo.reward.CeoRewardHistoryResponse;
import im.fooding.app.service.ceo.reward.CeoRewardService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag( name = "CEO Reward Controller", description = "CEO의 리워드에 관련된 컨트롤러" )
@RequestMapping("/ceo/reward")
public class CeoRewardController {
    private final CeoRewardService service;

    @GetMapping( "/store/{id}/coupon" )
    @Operation( summary = "해당 가게의 쿠폰 로그 조회" )
    public ApiResult<List<CeoRewardHistoryResponse>> retrieveCouponHistoryByStoreId(
        @PathVariable long id
    ){
        return ApiResult.ok( service.retrieveCouponHistoryByStoreId( id, true ) );
    }

    @GetMapping( "/store/{id}" )
    @Operation( summary = "해당 가게의 리워드 조회" )
    public ApiResult<List<CeoRewardHistoryResponse>> retrieveRewardHistoryByStoreId(
            @PathVariable long id
    ){
        return ApiResult.ok( service.retrieveCouponHistoryByStoreId( id, null ) );
    }
}
