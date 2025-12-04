package im.fooding.app.controller.pos.waiting;

import im.fooding.app.dto.request.pos.waiting.PosUpdateWaitingSettingActive;
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
@RequestMapping("/pos/stores/{storeId}/waitings")
@Tag(name = "PosWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class PosWaitingController {

    private final PosWaitingService posWaitingService;

    @GetMapping
    @Operation(summary = "웨이팅 목록 조회")
    ApiResult<PageResponse<PosStoreWaitingResponse>> list(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable long storeId,

            @ModelAttribute PosWaitingListRequest request
    ) {
        return ApiResult.ok(posWaitingService.list(storeId, request));
    }

    @GetMapping("/{storeWaitingId}")
    @Operation(summary = "웨이팅 상세 조회")
    public ApiResult<PosStoreWaitingResponse> details(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long storeWaitingId
    ) {
        return ApiResult.ok(posWaitingService.details(storeWaitingId));
    }

    @GetMapping("/{storeWaitingId}/logs")
    @Operation(summary = "웨이팅 로그 리스트 조회")
    public ApiResult<PageResponse<PosWaitingLogResponse>> listLogs(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long storeWaitingId,

            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(posWaitingService.listLogs(storeWaitingId, search));
    }

    @PostMapping("/{storeWaitingId}/cancel")
    @Operation(summary = "웨이팅 취소")
    ApiResult<Void> cancel(
            @Parameter(description = "가게 Id", example = "1")
            @PathVariable long storeId,

            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long storeWaitingId,

            @RequestBody @Validated PosWaitingCancelRequest request
    ) {
        posWaitingService.cancel(storeId, storeWaitingId, request.reason());
        return ApiResult.ok();
    }

    @PostMapping("/{storeWaitingId}/call")
    @Operation(summary = "웨이팅 호출")
    ApiResult<Void> call(
            @Parameter(description = "가게 Id", example = "1")
            @PathVariable long storeId,

            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long storeWaitingId
    ) {
        posWaitingService.call(storeId, storeWaitingId);
        return ApiResult.ok();
    }

    @PatchMapping("/settings/{waitingSettingId}/status")
    @Operation(summary = "웨이팅 상태 변경")
    ApiResult<Void> updateWaitingStatus(
            @Parameter(description = "웨이팅 세팅 ID", example = "1")
            @PathVariable long waitingSettingId,

            @RequestBody @Valid PosWaitingStatusUpdateRequest request
    ) {
        posWaitingService.updateWaitingStatus(waitingSettingId, request.status());
        return ApiResult.ok();
    }

    @PostMapping("/{storeWaitingId}/revert")
    @Operation(summary = "웨이팅 되돌리기")
    ApiResult<Void> revert(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long storeWaitingId
    ) {
        posWaitingService.revert(storeWaitingId);
        return ApiResult.ok();
    }

    @PostMapping("/{storeWaitingId}/seat")
    @Operation(summary = "웨이팅 착석")
    ApiResult<Void> seat(
            @Parameter(description = "가게 Id", example = "1")
            @PathVariable long storeId,

            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long storeWaitingId
    ) {
        posWaitingService.seat(storeId, storeWaitingId);
        return ApiResult.ok();
    }

    @PostMapping
    @Operation(summary = "웨이팅 등록")
    ApiResult<Void> register(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable long storeId,

            @RequestBody @Valid PosWaitingRegisterRequest request
    ) {
        posWaitingService.register(storeId, request);
        return ApiResult.ok();
    }

    @PatchMapping("/{storeWaitingId}/contact-info")
    @Operation(summary = "웨이팅 사용자 정보 수정")
    ApiResult<Void> updateContactInfo(
            @Parameter(description = "가게 Id", example = "1")
            @PathVariable long storeId,

            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long storeWaitingId,

            @RequestBody PosUpdateWaitingContactInfoRequest request
    ) {
        posWaitingService.updateContactInfo(storeId, storeWaitingId, request);
        return ApiResult.ok();
    }

    @PatchMapping("/{storeWaitingId}/memo")
    @Operation(summary = "웨이팅 메모 업데이트")
    ApiResult<Void> updateMemo(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long storeWaitingId,

            @RequestBody @Valid PosWaitingMemoUpdateRequest request
    ) {
        posWaitingService.updateMemo(storeWaitingId, request.memo());
        return ApiResult.ok();
    }

    @PatchMapping("/{storeWaitingId}/occupancy")
    @Operation(summary = "웨이팅 인원 변경")
    ApiResult<Void> updateOccupancy(
            @Parameter(description = "가게 웨이팅 id", example = "1")
            @PathVariable long storeWaitingId,

            @RequestBody @Valid PosWaitingOccupancyUpdateRequest request
    ) {
        posWaitingService.updateOccupancy(storeWaitingId, request);
        return ApiResult.ok();
    }

    @PatchMapping("/settings/{waitingSettingId}/status/waiting-time")
    @Operation(summary = "웨이팅 대기 시간 조정")
    ApiResult<Void> updateWaitingTime(
            @Parameter(description = "웨이팅 세팅 id", example = "1")
            @PathVariable long waitingSettingId,

            @RequestBody @Valid PosUpdateWaitingTimeRequest request
    ) {
        posWaitingService.updateWaitingTime(waitingSettingId, request.estimatedWaitingTimeMinutes());
        return ApiResult.ok();
    }

    @PatchMapping("/settings/{waitingSettingId}/active")
    @Operation(summary = "웨이팅 활성 상태 수정")
    ApiResult<Void> activateWaitingSetting(
            @Parameter(description = "웨이팅 세팅 id", example = "1")
            @PathVariable long waitingSettingId,

            @RequestBody @Valid PosUpdateWaitingSettingActive request
    ) {
        posWaitingService.updateWaitingSettingActive(waitingSettingId, request.active());
        return ApiResult.ok();
    }

    @PostMapping("/call-number/reset")
    @Operation(summary = "웨이팅 호출 번호 초기화")
    ApiResult<Void> resetWaitingCallNumber(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable long storeId
    ) {
        posWaitingService.resetWaitingCallNumber(storeId);
        return ApiResult.ok();
    }
}
