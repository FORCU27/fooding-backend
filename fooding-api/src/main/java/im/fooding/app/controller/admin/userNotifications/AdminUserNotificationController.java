package im.fooding.app.controller.admin.userNotifications;

import im.fooding.app.dto.request.admin.userNotifications.AdminCreateUserNotificationRequest;
import im.fooding.app.dto.response.admin.notification.AdminUserNotificationResponse;
import im.fooding.app.service.admin.userNotifications.AdminUserNotificationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user-notifications")
@Tag(name = "AdminUserNotificationController", description = "관리자 유저 알림 컨트롤러")
@Slf4j
public class AdminUserNotificationController {

    private final AdminUserNotificationService adminUserNotificationService;

    @GetMapping
    @Operation(summary = "유저 알림 전체 조회")
    public ApiResult<PageResponse<AdminUserNotificationResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "RECENT") String sortType,
            @RequestParam(required = false, defaultValue = "DESCENDING") SortDirection sortDirection) {
        PageResponse<AdminUserNotificationResponse> notifications = adminUserNotificationService.list(pageable,
                sortType, sortDirection);
        return ApiResult.ok(notifications);
    }

    @GetMapping("/{id}")
    @Operation(summary = "유저 알림 상세 조회")
    public ApiResult<AdminUserNotificationResponse> findById(@PathVariable Long id) {
        AdminUserNotificationResponse response = adminUserNotificationService.retrieve(id);
        return ApiResult.ok(response);
    }

    @PostMapping
    @Operation(summary = "유저 알림 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreateUserNotificationRequest request) {
        Long id = adminUserNotificationService.create(request);
        return ApiResult.ok(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "유저 알림 삭제")
    public ApiResult<Void> delete(@PathVariable long id) {
        adminUserNotificationService.delete(id);
        return ApiResult.ok();
    }
}