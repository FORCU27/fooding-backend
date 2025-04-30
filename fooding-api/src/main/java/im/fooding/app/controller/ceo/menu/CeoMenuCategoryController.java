package im.fooding.app.controller.ceo.menu;

import im.fooding.app.dto.request.ceo.menu.CeoSortMenuCategoryRequest;
import im.fooding.app.dto.response.ceo.menu.CeoMenuCategoryResponse;
import im.fooding.app.service.ceo.menu.CeoMenuCategoryService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import im.fooding.core.model.menu.MenuCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/stores")
@Tag(name = "CeoMenuCategoryController", description = "점주 메뉴 카테고리 컨트롤러")
public class CeoMenuCategoryController {

    private final CeoMenuCategoryService service;

    @PostMapping("/{storeId}/menu-categorys")
    @Operation(summary = "메뉴 카테고리 생성", description = "메뉴에 대한 카테고리 생성")
    public ApiResult<Long> create(
            @PathVariable Long storeId,
            @NotBlank(message = "카테고리 이름은 필수입니다.")
            @RequestParam ("categoryName") String categoryName
    ) {
        return ApiResult.ok(service.create(storeId, categoryName));
    }

    @PatchMapping("/menu-categorys/{categoryId}")
    @Operation(summary = "메뉴 카테고리 수정", description = "메뉴에 대한 카테고리 수정")
    public ApiResult<Long> update(
            @PathVariable Long categoryId,
            @NotBlank(message = "카테고리 이름은 필수입니다.")
            @RequestParam ("categoryName") String categoryName
    ) {
        return ApiResult.ok(service.update(categoryId, categoryName));
    }

    @DeleteMapping("/menu-categorys/{categoryId}")
    @Operation(summary = "메뉴 카테고리 삭제", description = "메뉴에 대한 카테고리 삭제")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable Long categoryId
    ) {
        service.delete(userInfo.getId(), categoryId);
        return ApiResult.ok();
    }

    @GetMapping("/{storeId}/menu-categorys")
    @Operation(summary = "메뉴 카테고리 목록 조회", description = "메뉴에 대한 카테고리 목록 조회")
    public ApiResult<List<CeoMenuCategoryResponse>> list(
            @PathVariable Long storeId
    ) {
        return ApiResult.ok(service.list(storeId));
    }

    @PostMapping("/menu-categorys/sort")
    @Operation(summary = "메뉴 카테고리 정렬", description = "요청한 메뉴 카테고리 ID 순서대로 재정렬")
    public ApiResult<Void> sort(
            @Valid @RequestBody CeoSortMenuCategoryRequest request
    ) {
        service.sort(request);
        return ApiResult.ok();
    }
}
