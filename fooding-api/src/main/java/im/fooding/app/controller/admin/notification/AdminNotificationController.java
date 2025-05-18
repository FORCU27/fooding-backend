package im.fooding.app.controller.admin.notification;

import im.fooding.app.dto.request.admin.notification.AdminCreateNotificationRequest;
import im.fooding.app.dto.request.admin.notification.AdminUpdateNotificationRequest;
import im.fooding.app.dto.response.admin.notification.AdminNotificationResponse;
import im.fooding.app.service.admin.notification.AdminNotificationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.model.notification.NotificationSortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notifications")
@Tag(name = "AdminNotificationController", description = "관리자 알림 컨트롤러")
@Slf4j
public class AdminNotificationController {

  private final AdminNotificationService adminNotificationService;

  @GetMapping
  @Operation(summary = "알림 전체 조회")
  public ApiResult<PageResponse<AdminNotificationResponse>> findAll(
      Pageable pageable,
      @RequestParam(required = false, defaultValue = "RECENT") NotificationSortType sortType,
      @RequestParam(required = false, defaultValue = "DESCENDING") SortDirection sortDirection) {
    PageResponse<AdminNotificationResponse> notifications = adminNotificationService.list(pageable, sortType,
        sortDirection);
    return ApiResult.ok(notifications);
  }

  @GetMapping("/{id}")
  @Operation(summary = "알림 상세 조회")
  public ApiResult<AdminNotificationResponse> findById(@PathVariable Long id) {
    AdminNotificationResponse response = adminNotificationService.retrieve(id);
    return ApiResult.ok(response);
  }

  @PostMapping
  @Operation(summary = "알림 생성")
  public ApiResult<Long> create(@RequestBody @Valid AdminCreateNotificationRequest request) {
    Long id = adminNotificationService.create(request);
    return ApiResult.ok(id);
  }

  @PutMapping("/{id}")
  @Operation(summary = "알림 수정")
  public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Valid AdminUpdateNotificationRequest request) {
    adminNotificationService.update(id, request);
    return ApiResult.ok();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "알림 삭제")
  public ApiResult<Void> delete(@PathVariable long id, @AuthenticationPrincipal UserInfo userInfo) {
    adminNotificationService.delete(id, userInfo.getId());
    return ApiResult.ok();
  }
}
