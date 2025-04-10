package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.WaitingListByStoreIdRequest;
import im.fooding.app.dto.response.waiting.WaitingResponse;
import im.fooding.app.service.waiting.AppWaitingApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/waitings")
@Tag(name = "AppWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class AppWaitingController {

    private final AppWaitingApplicationService appWaitingApplicationService;

    @GetMapping("/store/{storeId}")
    @Operation(summary = "웨이팅 목록 조회")
    ApiResult<PageResponse<WaitingResponse>> listByStoreId(
            @Parameter(description = "가게 id", example = "1")
            @PathVariable long storeId,

            @RequestBody WaitingListByStoreIdRequest request
    ) {
        return ApiResult.ok(appWaitingApplicationService.listByStoreIdAndStatus(storeId, request));
    }
}
