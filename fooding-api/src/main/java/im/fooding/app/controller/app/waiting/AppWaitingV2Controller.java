package im.fooding.app.controller.app.waiting;

import im.fooding.app.dto.request.app.waiting.AppWaitingRegisterRequestV2;
import im.fooding.app.dto.response.app.waiting.AppWaitingRegisterResponseV2;
import im.fooding.app.service.app.waiting.AppWaitingV2Service;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/app/waitings")
@Tag(name = "AppWaitingController V2", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class AppWaitingV2Controller {

    private final AppWaitingV2Service appWaitingV2Service;

    @PostMapping("/stores/{storeId}/requests")
    @Operation(summary = "웨이팅 등록")
    ApiResult<AppWaitingRegisterResponseV2> register(
            @Parameter(description = "가게 id", example = "1")
            @PathVariable long storeId,

            @RequestBody @Valid AppWaitingRegisterRequestV2 request
    ) {
        return ApiResult.ok(appWaitingV2Service.register(storeId, request));
    }
}
