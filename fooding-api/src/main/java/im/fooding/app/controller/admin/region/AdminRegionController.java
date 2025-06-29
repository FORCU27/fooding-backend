package im.fooding.app.controller.admin.region;

import im.fooding.app.dto.request.admin.region.AdminRegionCreateRequest;
import im.fooding.app.dto.request.admin.region.AdminRegionListRequest;
import im.fooding.app.dto.request.admin.region.AdminRegionResponse;
import im.fooding.app.dto.request.admin.region.AdminRegionUpdateRequest;
import im.fooding.app.service.admin.region.AdminRegionService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/admin/regions")
@Tag(name = "AdminRegionController", description = "관리자 지역 컨트롤러")
@Slf4j
public class AdminRegionController {

    private final AdminRegionService adminRegionService;

    @PostMapping
    @Operation(summary = "지역 생성")
    public ApiResult<Void> create(
            @RequestBody @Valid AdminRegionCreateRequest request
    ) {
        adminRegionService.create(request);
        return ApiResult.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "지역 조회")
    public ApiResult<AdminRegionResponse> get(
            @PathVariable String id
    ) {
        return ApiResult.ok(adminRegionService.get(id));
    }

    @GetMapping
    @Operation(summary = "지역 조회(page)")
    public ApiResult<PageResponse<AdminRegionResponse>> list(
            @Valid AdminRegionListRequest request
    ) {
        return ApiResult.ok(adminRegionService.list(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "지역 수정")
    public ApiResult<AdminRegionResponse> update(
            @PathVariable String id,

            @RequestBody @Valid AdminRegionUpdateRequest request
    ) {
        adminRegionService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "지역 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable String id
    ) {
        adminRegionService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
