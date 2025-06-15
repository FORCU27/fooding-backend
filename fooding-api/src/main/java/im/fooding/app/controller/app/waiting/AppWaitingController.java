package im.fooding.app.controller.app.waiting;

import im.fooding.app.dto.request.app.waiting.AppWaitingListRequest;
import im.fooding.app.dto.request.app.waiting.AppWaitingRegisterRequest;
import im.fooding.app.dto.response.app.waiting.*;
import im.fooding.app.service.app.waiting.AppWaitingApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import im.fooding.core.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/waitings")
@Tag(name = "AppWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class AppWaitingController {

    private final AppWaitingApplicationService appWaitingApplicationService;

    @GetMapping("/{id}/requests")
    @Operation(summary = "웨이팅 목록 조회")
    ApiResult<PageResponse<AppStoreWaitingResponse>> list(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @ModelAttribute AppWaitingListRequest request
    ) {
        return ApiResult.ok(appWaitingApplicationService.list(id, request));
    }

    @GetMapping("/requests/{requestId}")
    @Operation(summary = "웨이팅 상세 조회")
    public ApiResult<AppStoreWaitingResponse> details(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        return ApiResult.ok(appWaitingApplicationService.details(requestId));
    }

    @GetMapping("/requests/{requestId}/logs")
    @Operation(summary = "웨이팅 로그 조회")
    public ApiResult<PageResponse<AppWaitingLogResponse>> listLogs(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId,

            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(appWaitingApplicationService.listLogs(requestId, search));
    }

    @PostMapping("/{id}/requests")
    @Operation(summary = "웨이팅 등록")
    ApiResult<AppWaitingRegisterResponse> register(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @RequestBody @Valid AppWaitingRegisterRequest request
    ) {
        return ApiResult.ok(appWaitingApplicationService.register(id, request));
    }

    @GetMapping("/{id}/overview")
    @Operation(summary = "웨이팅 현황 조회")
    ApiResult<AppWaitingOverviewResponse> overview(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id
    ) {
        return ApiResult.ok(appWaitingApplicationService.overview(id));
    }

    @GetMapping("/store/{storeId}/waiting-status")
    @Operation(summary = "현재 가게에 남아있는 웨이팅 목록 조회")
    ApiResult<List<AppWaitingStatusResponse>> waitingStatus(
        @PathVariable long storeId
    ){
        return ApiResult.ok( appWaitingApplicationService.waitingStatus( storeId ) );
    }
}
