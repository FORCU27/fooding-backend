package im.fooding.app.controller.admin.banner;


import im.fooding.app.dto.request.admin.banner.AdminBannerCreateRequest;
import im.fooding.app.dto.request.admin.banner.AdminBannerPageRequest;
import im.fooding.app.dto.request.admin.banner.AdminBannerUpdateRequest;
import im.fooding.app.dto.response.admin.banner.AdminBannerResponse;
import im.fooding.app.service.admin.banner.AdminBannerService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/banners")
@Tag(name = "AdminBannerController", description = "Admin Banner Controller")
public class AdminBannerController {

    private final AdminBannerService adminBannerService;

    @PostMapping
    @Operation(summary = "배너 생성")
    public ApiResult<String> create(
            @RequestBody @Valid AdminBannerCreateRequest request
    ) {
        return ApiResult.ok(adminBannerService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "배너 조회")
    public ApiResult<AdminBannerResponse> get(
            @PathVariable String id
    ) {
        return ApiResult.ok(adminBannerService.get(id));
    }

    @GetMapping
    @Operation(summary = "배너 조회(page)")
    public ApiResult<PageResponse<AdminBannerResponse>> list(
            @Valid AdminBannerPageRequest request
    ) {
        return ApiResult.ok(adminBannerService.list(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "배너 수정")
    public ApiResult<Void> update(
            @PathVariable String id,
            @RequestBody @Valid AdminBannerUpdateRequest request
    ) {
        adminBannerService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "배너 제거")
    public ApiResult<Void> delete(
            @PathVariable String id,
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        adminBannerService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
