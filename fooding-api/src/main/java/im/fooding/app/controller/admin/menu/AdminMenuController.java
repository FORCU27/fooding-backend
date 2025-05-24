package im.fooding.app.controller.admin.menu;

import im.fooding.app.dto.request.admin.menu.AdminMenuCreateRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuUpdateRequest;
import im.fooding.app.dto.response.admin.menu.AdminMenuResponse;
import im.fooding.app.service.admin.menu.AdminMenuService;
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
@RequestMapping("/admin/menus")
@Tag(name = "AdminMenuController", description = "[관리자] 메뉴 컨트롤러")
@Slf4j
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @PostMapping
    @Operation(summary = "웨이팅 생성")
    public ApiResult<Void> create(
            @RequestBody @Valid AdminMenuCreateRequest request
    ) {
        adminMenuService.create(request);
        return ApiResult.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "웨이팅 조회")
    public ApiResult<AdminMenuResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminMenuService.get(id));
    }

    @GetMapping
    @Operation(summary = "웨이팅 조회(page)")
    public ApiResult<PageResponse<AdminMenuResponse>> list(
            @Parameter(description = "검색 및 페이징 조건")
            @ModelAttribute BasicSearch search
    ) {
        return ApiResult.ok(adminMenuService.list(search));
    }

    @PutMapping("/{id}")
    @Operation(summary = "웨이팅 수정")
    public ApiResult<AdminMenuResponse> update(
            @PathVariable long id,

            @RequestBody @Valid AdminMenuUpdateRequest request
    ) {
        adminMenuService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "웨이팅 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        adminMenuService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
