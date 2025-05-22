package im.fooding.app.controller.admin.user;

import im.fooding.app.dto.request.admin.user.AdminUpdateUserRequest;
import im.fooding.app.dto.response.admin.user.AdminUserResponse;
import im.fooding.app.service.admin.user.AdminUserService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Tag(name = "AdminUserController", description = "관리자 유저 관리 컨트롤러")
@Slf4j
public class AdminUserController {

    private final AdminUserService adminUserService;

//    private final AdminAuthApplicationService adminAuthApplicationService;
//
//    @PostMapping
//    @Operation(summary = "유저 생성")
//    public ApiResult<Void> create(@RequestBody @Valid AdminCreateManagerRequest adminCreateManagerRequest) {
//        // TODO: 변경
//        adminAuthApplicationService.register(adminCreateManagerRequest);
//        return ApiResult.ok();
//    }

    @GetMapping
    @Operation(summary = "유저 목록 조회")
    public ApiResult<PageResponse<AdminUserResponse>> list(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "RECENT") String sortType,
            @RequestParam(required = false, defaultValue = "DESCENDING") SortDirection sortDirection) {
        PageResponse<AdminUserResponse> users = adminUserService.list(pageable, sortType, sortDirection);
        return ApiResult.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "유저 상세 조회")
    public ApiResult<AdminUserResponse> findById(@PathVariable Long id) {
        AdminUserResponse user = adminUserService.findById(id);
        return ApiResult.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "유저 정보 수정")
    public ApiResult<Void> update(
            @PathVariable Long id,
            @RequestBody @Valid AdminUpdateUserRequest request) {
        adminUserService.update(id, request);
        return ApiResult.ok();
    }
}
