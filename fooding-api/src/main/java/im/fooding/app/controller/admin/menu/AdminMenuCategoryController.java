package im.fooding.app.controller.admin.menu;

import im.fooding.app.dto.request.admin.menu.AdminMenuCategoryCreateRequest;
import im.fooding.app.dto.request.admin.menu.AdminMenuCreateRequest;
import im.fooding.app.dto.response.admin.menu.AdminMenuCategoryResponse;
import im.fooding.app.dto.response.admin.menu.AdminMenuResponse;
import im.fooding.app.service.admin.menu.AdminMenuCategoryService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/menu-categories")
@Tag(name = "AdminMenuCategoryController", description = "관리자 메뉴 카테고리 컨트롤러")
@Slf4j
public class AdminMenuCategoryController {

    private final AdminMenuCategoryService adminMenuCategoryService;

    @PostMapping
    @Operation(summary = "메뉴 카테고리 생성")
    public ApiResult<Void> create(
            @RequestBody @Valid AdminMenuCategoryCreateRequest request
    ) {
        adminMenuCategoryService.create(request);
        return ApiResult.ok();
    }


    @GetMapping("/{id}")
    @Operation(summary = "메뉴 카테고리 조회")
    public ApiResult<AdminMenuCategoryResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(adminMenuCategoryService.get(id));
    }
}
