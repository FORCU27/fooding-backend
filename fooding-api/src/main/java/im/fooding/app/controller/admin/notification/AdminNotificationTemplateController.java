package im.fooding.app.controller.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationTemplateRequest;
import im.fooding.app.dto.request.admin.notification.AdminNotificationTemplatePageRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationTemplateRequest;
import im.fooding.app.dto.response.admin.notification.AdminNotificationTemplateResponse;
import im.fooding.app.service.admin.notification.AdminNotificationTemplateService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notification-template")
@Tag(name = "AdminNotificationTemplateController", description = "관리자 알림 양식 컨트롤러")
public class AdminNotificationTemplateController {

    private final AdminNotificationTemplateService adminNotificationTemplateService;

    @GetMapping
    @Operation(summary = "알림 양식 전체 조회")
    public ApiResult<PageResponse<AdminNotificationTemplateResponse>> findAll(AdminNotificationTemplatePageRequest request) {
        PageResponse<AdminNotificationTemplateResponse> notificationTemplates = adminNotificationTemplateService.list(request);
        return ApiResult.ok(notificationTemplates);
    }

    @GetMapping("/{id}")
    @Operation(summary = "알림 양식 상세 조회")
    public ApiResult<AdminNotificationTemplateResponse> retrieve(@PathVariable String id) {
        AdminNotificationTemplateResponse response = adminNotificationTemplateService.retrieve(id);
        return ApiResult.ok(response);
    }

    @PostMapping
    @Operation(summary = "알림 양식 생성")
    public ApiResult<String> create(@RequestBody @Valid AdminCreateNotificationTemplateRequest request) {
        String id = adminNotificationTemplateService.create(request);
        return ApiResult.ok(id);
    }

    @PostMapping("/{id}")
    @Operation(summary = "알림 양식 수정")
    public ApiResult<Void> update(@PathVariable String id, @RequestBody @Valid AdminUpdateNotificationTemplateRequest request) {
        adminNotificationTemplateService.update(id, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "알림 양식 삭제")
    public ApiResult<Void> delete(@PathVariable String id, @AuthenticationPrincipal UserInfo userInfo) {
        adminNotificationTemplateService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
