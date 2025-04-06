package im.fooding.app.controller.admin.managers;

import im.fooding.app.dto.request.admin.manager.AdminUpdateMangerRequest;
import im.fooding.app.dto.response.admin.manager.AdminManagerResponse;
import im.fooding.app.service.admin.manager.AdminManagerApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/managers")
@Tag(name = "AdminManagerController", description = "관리자 매니저 컨트롤러")
@Slf4j
public class AdminManagerController {
    private final AdminManagerApplicationService service;

    @GetMapping("/me")
    @Operation(summary = "로그인한 정보")
    public ApiResult<AdminManagerResponse> me(@AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(userInfo.getId()));
    }

    @GetMapping
    @Operation(summary = "관리자 리스트")
    public ApiResult<PageResponse> list(BasicSearch search) {
        return ApiResult.ok(service.list(search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "관리자 조회")
    public ApiResult<AdminManagerResponse> retrieve(@PathVariable long id) {
        return ApiResult.ok(service.retrieve(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "관리자 수정")
    public ApiResult<Void> update(@PathVariable long id, @RequestBody @Valid AdminUpdateMangerRequest adminUpdateMangerRequest) {
        service.update(id, adminUpdateMangerRequest);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "관리자 삭제")
    public ApiResult<Void> delete(@PathVariable long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
