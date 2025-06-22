package im.fooding.app.controller.user.store;

import im.fooding.app.dto.response.user.store.UserStoreInformationResponse;
import im.fooding.app.service.user.store.UserStoreInformationService;
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
@Tag(name = "UserStoreInformationController", description = "User Store 부가정보 컨트롤러")
public class UserStoreInformationController {
    private final UserStoreInformationService service;

    @GetMapping("/{storeId}/information")
    @Operation(summary = "부가정보 조회")
    public ApiResult<UserStoreInformationResponse> retrieve(@PathVariable long storeId) {
        return ApiResult.ok(service.retrieve(storeId));
    }
}
