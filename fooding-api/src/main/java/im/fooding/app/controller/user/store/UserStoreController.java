package im.fooding.app.controller.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStoreRequest;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.app.service.user.store.UserStoreService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/stores")
@Tag(name = "UserStoreController", description = "유저 스토어 컨트롤러")
public class UserStoreController {
    private final UserStoreService service;

    @GetMapping
    @Operation(summary = "가게 목록 조회")
    public ApiResult<PageResponse<UserStoreListResponse>> list(@Valid UserSearchStoreRequest request) {
        return ApiResult.ok(service.list(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 단건조회")
    public ApiResult<UserStoreResponse> retrieve(@PathVariable Long id) {
        return ApiResult.ok(service.retrieve(id));
    }
}
