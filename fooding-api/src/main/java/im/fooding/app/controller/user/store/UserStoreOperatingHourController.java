package im.fooding.app.controller.user.store;

import im.fooding.app.dto.response.user.store.UserStoreOperatingHourResponse;
import im.fooding.app.service.user.store.UserStoreOperatingHourService;
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
@RequestMapping("/user/stores")
@Tag(name = "UserStoreOperatingHourController", description = "User Store 영업시간/휴무일 컨트롤러")
public class UserStoreOperatingHourController {
    private final UserStoreOperatingHourService service;

    @GetMapping("/{storeId}/operating-hour")
    @Operation(summary = "영업시간/휴무일 조회")
    public ApiResult<UserStoreOperatingHourResponse> retrieve(@PathVariable long storeId) {
        return ApiResult.ok(service.retrieve(storeId));
    }
}
