package im.fooding.app.controller.waiting;

import im.fooding.app.dto.request.waiting.WaitingRegisterRequest;
import im.fooding.app.dto.request.waiting.WaitingRegisterServiceRequest;
import im.fooding.app.dto.response.waiting.WaitingRegisterResponse;
import im.fooding.app.dto.response.waiting.WaitingRegisterServiceResponse;
import im.fooding.app.service.waiting.WaitingApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/waiting")
@Tag(name = "WaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class WaitingController {

    private final WaitingApplicationService waitingApplicationService;

    @PostMapping
    @Operation(summary = "웨이팅 등록")
    ApiResult<WaitingRegisterResponse> register(@RequestBody @Valid WaitingRegisterRequest request) {
        WaitingRegisterServiceRequest serviceRequest = request.toWaitingRegisterServiceRequest();
        WaitingRegisterServiceResponse response = waitingApplicationService.register(serviceRequest);

        return ApiResult.ok(WaitingRegisterResponse.from(response));
    }
}
