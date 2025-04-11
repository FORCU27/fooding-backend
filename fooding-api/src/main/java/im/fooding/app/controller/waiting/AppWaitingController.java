package im.fooding.app.controller.waiting;

import im.fooding.app.dto.response.waiting.AppWaitingDetailsResponse;
import im.fooding.app.service.waiting.AppWaitingApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/{id}")
    public ApiResult<AppWaitingDetailsResponse> details(
            @Parameter(description = "웨이팅 id", example = "1")
            @PathVariable long id
    ) {
        return ApiResult.ok(appWaitingApplicationService.details(id));
    }
}
