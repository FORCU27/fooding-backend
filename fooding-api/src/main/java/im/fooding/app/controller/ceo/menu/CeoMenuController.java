package im.fooding.app.controller.ceo.menu;

import im.fooding.app.dto.request.ceo.menu.CeoMenuCreateRequest;
import im.fooding.app.dto.request.ceo.menu.CeoMenuListRequest;
import im.fooding.app.dto.request.ceo.menu.CeoMenuUpdateRequest;
import im.fooding.app.dto.response.ceo.menu.CeoMenuResponse;
import im.fooding.app.service.ceo.menu.CeoMenuService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/ceo/menus")
@Tag(name = "CeoMenuController", description = "CEO Menu Controller")
public class CeoMenuController {

    private final CeoMenuService ceoMenuService;

    @PostMapping
    @Operation(summary = "메뉴 생성")
    public ApiResult<Void> create(
            @RequestBody @Valid CeoMenuCreateRequest request
    ) {
        ceoMenuService.create(request);
        return ApiResult.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "메뉴 조회")
    public ApiResult<CeoMenuResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(ceoMenuService.get(id));
    }

    @GetMapping
    @Operation(summary = "메뉴 조회(page)")
    public ApiResult<PageResponse<CeoMenuResponse>> list(
            @Parameter(description = "검색 및 페이징 조건 (storeId 포함)")
            @ModelAttribute CeoMenuListRequest request
    ) {
        return ApiResult.ok(ceoMenuService.list(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "메뉴 수정")
    public ApiResult<CeoMenuResponse> update(
            @PathVariable long id,

            @AuthenticationPrincipal UserInfo userInfo,
            @RequestBody @Valid CeoMenuUpdateRequest request
    ) {
        ceoMenuService.update(id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "메뉴 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        ceoMenuService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
