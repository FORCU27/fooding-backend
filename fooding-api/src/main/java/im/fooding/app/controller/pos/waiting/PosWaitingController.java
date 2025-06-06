package im.fooding.app.controller.pos.waiting;

import im.fooding.app.dto.request.pos.waiting.PosUpdateWaitingTimeRequest;
import im.fooding.app.dto.request.pos.waiting.PosUpdateWaitingContactInfoRequest;
import im.fooding.app.dto.response.pos.waiting.PosStoreWaitingResponse;
import im.fooding.app.dto.response.pos.waiting.PosWaitingLogResponse;
import im.fooding.app.service.pos.waiting.PosWaitingService;
import im.fooding.app.dto.request.pos.waiting.PosWaitingCancelRequest;
import im.fooding.app.dto.request.pos.waiting.PosWaitingOccupancyUpdateRequest;
import im.fooding.app.dto.request.pos.waiting.PosWaitingStatusUpdateRequest;
import im.fooding.app.dto.request.pos.waiting.PosWaitingRegisterRequest;
import im.fooding.app.dto.request.pos.waiting.PosWaitingMemoUpdateRequest;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import im.fooding.app.dto.request.pos.waiting.PosWaitingListRequest;
import im.fooding.core.common.PageResponse;
import jakarta.validation.Valid;
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
    ApiResult<PageResponse<PosStoreWaitingResponse>> list(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @ModelAttribute PosWaitingListRequest request
    ) {
        return ApiResult.ok(posWaitingService.list(id, request));
    }

    @GetMapping("/requests/{requestId}")
    @Operation(summary = "웨이팅 상세 조회")
    public ApiResult<PosStoreWaitingResponse> details(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        return ApiResult.ok(posWaitingService.details(requestId));
    }

    @GetMapping("/requests/{requestId}/logs")
    @Operation(summary = "웨이팅 로그 리스트 조회")
    public ApiResult<PageResponse<PosWaitingLogResponse>> listLogs(
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

    @PatchMapping("/{id}/status")
    @Operation(summary = "웨이팅 상태 변경")
    ApiResult<Void> updateWaitingStatus(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @RequestBody @Valid PosWaitingStatusUpdateRequest request
    ) {
        posWaitingService.updateWaitingStatus(id, request.status());
        return ApiResult.ok();
    }

    @PostMapping("/requests/{requestId}/revert")
    @Operation(summary = "웨이팅 되돌리기")
    ApiResult<Void> revert(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        posWaitingService.revert(requestId);
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

    @PatchMapping("/requests/{requestId}/contact-info")
    @Operation(summary = "웨이팅 사용자 정보 수정")
    ApiResult<Void> updateContactInfo(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId,

            @RequestBody PosUpdateWaitingContactInfoRequest request
    ) {
        posWaitingService.updateContactInfo(requestId, request);
        return ApiResult.ok();
    }

    @PatchMapping("/requests/{requestId}/memo")
    @Operation(summary = "웨이팅 메모 업데이트")
    ApiResult<Void> updateMemo(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId,

            @RequestBody @Valid PosWaitingMemoUpdateRequest request
    ) {
        posWaitingService.updateMemo(requestId, request.memo());
        return ApiResult.ok();
    }

    @PatchMapping("/requests/{requestId}/occupancy")
    @Operation(summary = "웨이팅 인원 변경")
    ApiResult<Void> updateOccupancy(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long requestId,

            @RequestBody @Valid PosWaitingOccupancyUpdateRequest request
    ) {
        posWaitingService.updateOccupancy(requestId, request);
        return ApiResult.ok();
    }

    @PatchMapping("/{id}/waiting-time")
    @Operation(summary = "웨이팅 대기 시간 조정")
    ApiResult<Void> updateWaitingTime(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @RequestBody @Valid PosUpdateWaitingTimeRequest request
    ) {
        posWaitingService.updateWaitingTime(id, request.estimatedWaitingTimeMinutes());
        return ApiResult.ok();
    }
}
