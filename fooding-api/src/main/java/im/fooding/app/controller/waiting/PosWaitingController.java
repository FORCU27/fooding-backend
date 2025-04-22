package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.PosWaitingRegisterRequest;
import im.fooding.app.service.waiting.PosWaitingService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import im.fooding.app.dto.request.waiting.WaitingListRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.core.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/{id}/requests")
    @Operation(summary = "웨이팅 등록")
    ApiResult<Void> register(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @RequestBody @Valid PosWaitingRegisterRequest request
    ) {
        posWaitingService.register(id, request);
        return ApiResult.ok();
    }
}
