package im.fooding.app.controller.admin.waiting;

import im.fooding.app.dto.request.admin.waiting.AdminWaitingUserCreateRequest;
import im.fooding.app.dto.request.admin.waiting.AdminWaitingUserUpdateRequest;
import im.fooding.app.dto.response.admin.waiting.AdminWaitingUserResponse;
import im.fooding.app.service.admin.waiting.AdminWaitingUserService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/waitings/users")
@Tag(name = "AdminWaitingController", description = "웨이팅 관리 컨트롤러")
@Slf4j
public class AdminWaitingUserController {

    private final AdminWaitingUserService adminWaitingUserService;

    @PostMapping
    @Operation(summary = "웨이팅 유저 생성")
    public ApiResult<AdminWaitingUserResponse> create(
            @RequestBody @Valid AdminWaitingUserCreateRequest request
    ) {
        AdminWaitingUserResponse response = adminWaitingUserService.create(request);
        return ApiResult.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "웨이팅 유저 조회")
    public ApiResult<AdminWaitingUserResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminWaitingUserService.get(id));
    }

    @GetMapping
    @Operation(summary = "웨이팅 유저 조회(page)")
    public ApiResult<PageResponse<AdminWaitingUserResponse>> getList(
            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(adminWaitingUserService.getList(search));
    }

    @PutMapping("/{id}")
    @Operation(summary = "웨이팅 유저 수정")
    public ApiResult<AdminWaitingUserResponse> update(
            @PathVariable long id,

            @RequestBody AdminWaitingUserUpdateRequest request
    ) {
        return ApiResult.ok(adminWaitingUserService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "웨이팅 유저 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminWaitingUserService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
