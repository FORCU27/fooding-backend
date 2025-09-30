package im.fooding.app.controller.user.waiting;

import im.fooding.app.dto.response.user.waiting.UserWaitingAvailableResponse;
import im.fooding.app.dto.response.user.waiting.UserWaitingServiceUsageResponse;
import im.fooding.app.service.user.waiting.UserWaitingService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/stores/{storeId}/waitings")
@Tag(name = "UserWaitingController", description = "유저 웨이팅 컨트롤러")
public class UserWaitingController {

    private final UserWaitingService userWaitingService;

    @GetMapping("/usage")
    @Operation(summary = "줄서기 서비스 이용 확인")
    ApiResult<UserWaitingServiceUsageResponse> checkServiceUsage(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable("storeId") long storeId
    ) {
        return ApiResult.ok(userWaitingService.checkServiceUsage(storeId));
    }

    @GetMapping("/available")
    @Operation(summary = "줄서기 가능 상태 확인")
    ApiResult<UserWaitingAvailableResponse> checkAvailability(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable("storeId") long storeId
    ) {
        return ApiResult.ok(userWaitingService.checkAvailability(storeId));
    }
}
