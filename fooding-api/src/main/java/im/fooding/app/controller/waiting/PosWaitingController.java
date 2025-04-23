package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.PosWaitingCancelRequest;
import im.fooding.app.dto.response.waiting.StoreWaitingResponse;
import im.fooding.app.dto.response.waiting.WaitingLogResponse;
import im.fooding.app.service.waiting.PosWaitingService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
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

    private final PosWaitingService posWaitingService;

    @GetMapping("/{id}/requests")
    @Operation(summary = "웨이팅 목록 조회")
    ApiResult<PageResponse<WaitingResponse>> list(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @ModelAttribute WaitingListRequest request
    ) {
        return ApiResult.ok(posWaitingService.list(id, request));
    }

    @GetMapping("/requests/{requestId}")
    @Operation(summary = "웨이팅 상세 조회")
    public ApiResult<StoreWaitingResponse> details(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        return ApiResult.ok(posWaitingService.details(requestId));
    }

    @GetMapping("/requests/{requestId}/logs")
    @Operation(summary = "웨이팅 로그 리스트 조회")
    public ApiResult<PageResponse<WaitingLogResponse>> listLogs(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId,

            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(posWaitingService.listLogs(requestId, search));
    }

    @PostMapping("/requests/{requestId}/cancel")
    @Operation(summary = "웨이팅 취소")
    ApiResult<Void> cancel(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId,

            @RequestBody @Validated PosWaitingCancelRequest request
    ) {
        posWaitingService.cancel(requestId, request.reason());
        return ApiResult.ok();
    }

    @PostMapping("/requests/{requestId}/call")
    @Operation(summary = "웨이팅 호출")
    ApiResult<Void> call(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        posWaitingService.call(requestId);
        return ApiResult.ok();
    }

    @PostMapping("/requests/{requestId}/seat")
    @Operation(summary = "웨이팅 착석")
    ApiResult<Void> seat(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        posWaitingService.seat(requestId);
        return ApiResult.ok();
    }
}
