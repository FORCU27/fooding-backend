package im.fooding.app.controller.app.store;

import im.fooding.app.dto.request.app.store.AppSearchStoreRequest;
import im.fooding.app.dto.response.app.store.AppStoreResponse;
import im.fooding.app.service.app.store.AppStoreService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/stores")
@Tag(name = "AppStoreController", description = "[APP] 가게 컨트롤러")
public class AppStoreController {
    private final AppStoreService service;

    @GetMapping
    @Operation(summary = "가게 리스트 조회")
    public ApiResult<List<AppStoreResponse>> list(
            @AuthenticationPrincipal UserInfo userInfo,
            AppSearchStoreRequest search
    ) {
        return ApiResult.ok(service.list(userInfo.getId(), search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 조회")
    public ApiResult<AppStoreResponse> retrieve(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(id, userInfo.getId()));
    }
}
