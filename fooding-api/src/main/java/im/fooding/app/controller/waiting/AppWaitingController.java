package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.AppWaitingRegisterRequest;
import im.fooding.app.dto.request.waiting.AppWaitingRegisterServiceRequest;
import im.fooding.app.dto.response.waiting.AppWaitingRegisterResponse;
import im.fooding.app.dto.response.waiting.AppWaitingRegisterServiceResponse;
import im.fooding.app.service.waiting.AppWaitingApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/waiting")
@Tag(name = "AppWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class AppWaitingController {

    private final AppWaitingApplicationService appWaitingApplicationService;

    @PostMapping
    @Operation(summary = "웨이팅 등록")
    ApiResult<AppWaitingRegisterResponse> register(@RequestBody @Valid AppWaitingRegisterRequest request) {
        AppWaitingRegisterServiceRequest serviceRequest = request.toWaitingRegisterServiceRequest();
        AppWaitingRegisterServiceResponse response = appWaitingApplicationService.register(serviceRequest);

        return ApiResult.ok(AppWaitingRegisterResponse.from(response));
    }
}
