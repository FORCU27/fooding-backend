package im.fooding.app.controller.user.waiting;

import im.fooding.app.dto.response.user.waiting.UserStoreWaitingResponse;
import im.fooding.app.service.user.waiting.UserStoreWaitingService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/store-waitings")
@Tag(name = "UserStoreWaitingController", description = "유저 스토어 웨이팅 컨트롤러")
public class UserStoreWaitingController {

    private final UserStoreWaitingService userWaitingService;

    @GetMapping("/{id}")
    @Operation(summary = "스토어 웨이팅 조회")
    public ApiResult<UserStoreWaitingResponse> getStoreWaiting(@PathVariable long id) {
        return ApiResult.ok(userWaitingService.getStoreWaiting(id));
    }
}
