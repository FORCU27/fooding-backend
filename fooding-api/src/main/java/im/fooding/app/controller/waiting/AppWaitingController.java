package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.AppWaitingRegisterRequest;
import im.fooding.app.dto.response.waiting.AppWaitingRegisterResponse;
import im.fooding.app.service.waiting.AppWaitingApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import im.fooding.app.dto.request.waiting.WaitingListRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.core.common.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import im.fooding.app.dto.response.waiting.StoreWaitingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/waitings")
@Tag(name = "AppWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class AppWaitingController {

    private final AppWaitingApplicationService appWaitingApplicationService;

    @GetMapping("/{id}/requests")
    @Operation(summary = "웨이팅 목록 조회")
    ApiResult<PageResponse<WaitingResponse>> list(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id,

            @ModelAttribute WaitingListRequest request
    ) {
        return ApiResult.ok(appWaitingApplicationService.list(id, request));
    }

    @GetMapping("/requests/{requestId}")
    public ApiResult<StoreWaitingResponse> details(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long requestId
    ) {
        return ApiResult.ok(appWaitingApplicationService.details(requestId));
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
}
