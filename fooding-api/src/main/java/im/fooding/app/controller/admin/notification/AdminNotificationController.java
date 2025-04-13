package im.fooding.app.controller.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationRequest;
import im.fooding.app.dto.response.admin.notification.AdminNotificationResponse;
import im.fooding.app.service.admin.notification.AdminNotificationApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notifications")
@Tag(name = "AdminNotificationController", description = "관리자 알림 컨트롤러")
@Slf4j
public class AdminNotificationController {

    private final AdminNotificationApplicationService adminNotificationApplicationService;

    @GetMapping
    @Operation(summary = "알림 전체 조회")
    public ApiResult<List<AdminNotificationResponse>> findAll(){
      List<AdminNotificationResponse> notifications = adminNotificationApplicationService.getAllNotifications();
      return ApiResult.ok(notifications);
    }

    @GetMapping("/{id}")
    @Operation(summary = "알림 상세 조회")
    public ApiResult<AdminNotificationResponse> findById(@PathVariable Long id) {
      AdminNotificationResponse response = adminNotificationApplicationService.getNotification(id);
      return ApiResult.ok(response);
    }

    @PostMapping
    @Operation(summary = "알림 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreateNotificationRequest request) {
      Long id = adminNotificationApplicationService.createNotification(request);
      return ApiResult.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "알림 수정")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Valid AdminUpdateNotificationRequest request) {
    adminNotificationApplicationService.updateNotification(id,request);
      return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "알림 삭제")
    public ApiResult<Void> delete(@PathVariable Long id) {
      adminNotificationApplicationService.deleteNotification(id);
        return ApiResult.ok();
      }
}
