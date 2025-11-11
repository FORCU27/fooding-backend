package im.fooding.app.controller.admin.storeNotification;

import im.fooding.app.dto.request.admin.storeNotification.AdminStoreNotificationCreateRequest;
import im.fooding.app.dto.request.admin.storeNotification.AdminStoreNotificationPageRequest;
import im.fooding.app.dto.response.admin.storeNotification.AdminStoreNotificationResponse;
import im.fooding.app.service.admin.storeNotification.AdminStoreNotificationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/store-notifications")
@Tag(name = "AdminStoreNotificationController", description = "관리자 가게 알림 컨트롤러")
@Slf4j
public class AdminStoreNotificationController {

    private final AdminStoreNotificationService adminStoreNotificationService;

    @GetMapping
    @Operation(summary = "가게 알림 페이징 조회")
    public ApiResult<PageResponse<AdminStoreNotificationResponse>> page(
            @ParameterObject @ModelAttribute AdminStoreNotificationPageRequest request
    ) {
        PageResponse<AdminStoreNotificationResponse> notifications = adminStoreNotificationService.page(request);

        return ApiResult.ok(notifications);
    }

    @PostMapping
    @Operation(summary = "가게 알림 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminStoreNotificationCreateRequest request) {
        Long id = adminStoreNotificationService.create(request);
        return ApiResult.ok(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 알림 삭제")
    public ApiResult<Void> delete(
            @PathVariable long id,
            @AuthenticationPrincipal UserInfo userInfo
    ) {
        adminStoreNotificationService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
