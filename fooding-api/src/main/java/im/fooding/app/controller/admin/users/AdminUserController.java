package im.fooding.app.controller.admin.users;

import im.fooding.app.dto.request.admin.user.AdminCreateUserRequest;
import im.fooding.app.dto.request.admin.user.AdminSearchUserRequest;
import im.fooding.app.dto.request.admin.user.AdminUpdateUserRequest;
import im.fooding.app.dto.response.admin.user.AdminUserResponse;
import im.fooding.app.service.admin.user.AdminUserService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Tag(name = "AdminUserController", description = "관리자 유저, 점주, 관리자 컨트롤러")
public class AdminUserController {
    private final AdminUserService service;

    @GetMapping
    @Operation(summary = "유저 리스트")
    public ApiResult<PageResponse> list(AdminSearchUserRequest search) {
        return ApiResult.ok(service.list(search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "유저 조회")
    public ApiResult<AdminUserResponse> retrieve(@PathVariable long id) {
        return ApiResult.ok(service.retrieve(id));
    }

    @PostMapping
    @Operation(summary = "유저 생성")
    public ApiResult<Long> save(@RequestBody @Valid AdminCreateUserRequest request) {
        return ApiResult.ok(service.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "유저 수정")
    public ApiResult<Void> update(@PathVariable long id, @RequestBody @Valid AdminUpdateUserRequest request) {
        service.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "유저 삭제")
    public ApiResult<Void> delete(@PathVariable long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
