package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.AppWaitingRegisterRequest;
import im.fooding.app.dto.response.waiting.AppWaitingRegisterResponse;
import im.fooding.app.service.waiting.AppWaitingApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
