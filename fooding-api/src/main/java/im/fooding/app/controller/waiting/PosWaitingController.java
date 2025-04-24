package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.PosWaitingCancelRequest;
import im.fooding.app.dto.request.waiting.PosWaitingStatusUpdateRequest;
import im.fooding.app.service.waiting.PosWaitingApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import im.fooding.app.dto.request.waiting.WaitingListRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.core.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pos/waitings")
@Tag(name = "PosWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class PosWaitingController {

    private final PosWaitingApplicationService posWaitingApplicationService;

    @GetMapping("/{id}/requests")
    @Operation(summary = "웨이팅 목록 조회")
    ApiResult<PageResponse<WaitingResponse>> list(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @ModelAttribute WaitingListRequest request
    ) {
        return ApiResult.ok(posWaitingApplicationService.list(id, request));
    }

    @PostMapping("/requests/{requestId}/cancel")
    @Operation(summary = "웨이팅 취소")
    ApiResult<Void> cancel(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId,

            @RequestBody @Validated PosWaitingCancelRequest request
    ) {
        posWaitingApplicationService.cancel(requestId, request.reason());
        return ApiResult.ok();
    }

    @PostMapping("/requests/{requestId}/call")
    @Operation(summary = "웨이팅 호출")
    ApiResult<Void> call(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        posWaitingApplicationService.call(requestId);
        return ApiResult.ok();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "웨이팅 상태 변경")
    ApiResult<Void> updateWaitingStatus(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            PosWaitingStatusUpdateRequest request
    ) {
        posWaitingApplicationService.updateWaitingStatus(id, request.status());
        return ApiResult.ok();
    }

    @PostMapping("/requests/{requestId}/revert")
    @Operation(summary = "웨이팅 되돌리기")
    ApiResult<Void> revert(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        posWaitingApplicationService.revert(requestId);
        return ApiResult.ok();
    }

    @PostMapping("/requests/{requestId}/seat")
    @Operation(summary = "웨이팅 착석")
    ApiResult<Void> seat(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        posWaitingApplicationService.seat(requestId);
        return ApiResult.ok();
    }
}
