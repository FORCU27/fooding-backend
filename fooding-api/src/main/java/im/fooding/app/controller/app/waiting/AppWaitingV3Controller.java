package im.fooding.app.controller.app.waiting;

import im.fooding.app.dto.request.app.waiting.AppWaitingRegisterRequestV3;
import im.fooding.app.dto.response.app.waiting.AppWaitingRegisterResponseV3;
import im.fooding.app.service.app.waiting.AppWaitingV3Service;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v3/app/waitings")
@Tag(name = "AppWaitingController V3", description = "웨이팅 관리 컨트롤러 (대기열 적용)")
@Slf4j
public class AppWaitingV3Controller {

    private final AppWaitingV3Service appWaitingV3Service;

    @PostMapping("/stores/{storeId}/requests")
    @Operation(summary = "웨이팅 등록 (대기열)")
    ApiResult<AppWaitingRegisterResponseV3> register(
            @Parameter(description = "가게 id", example = "1")
            @PathVariable long storeId,

            @RequestBody @Valid AppWaitingRegisterRequestV3 request
    ) {
        return ApiResult.ok(appWaitingV3Service.register(storeId, request));
    }

    @GetMapping("/status/{traceId}")
    @Operation(summary = "웨이팅 등록 상태 조회 (폴링)")
    public ApiResult<Map<String, Object>> getRegistrationStatus(
            @Parameter(description = "요청 추적 ID") @PathVariable String traceId
    ) {
        return ApiResult.ok(appWaitingV3Service.getRegistrationStatus(traceId));
    }
}
