package im.fooding.app.controller.user.notification;

import im.fooding.app.dto.response.user.notification.UserNotificationResponse;
import im.fooding.app.service.user.notification.UserNotificationApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/notifications")
@Tag(name = "UserNotificationController", description = "유저 알림 컨트롤러")
@Slf4j
public class UserNotificationController {

  private final UserNotificationApplicationService userNotificationApplicationService;

  @GetMapping
  @Operation(summary = "유저 알림 전체 조회")
  public ApiResult<PageResponse<UserNotificationResponse>> list(
      @AuthenticationPrincipal UserInfo userInfo,
      Pageable pageable) {
    Long userId = userInfo.getId();
    PageResponse<UserNotificationResponse> notifications = userNotificationApplicationService.list(userId, pageable);
    return ApiResult.ok(notifications);
  }

  @GetMapping("/{notificationId}")
  @Operation(summary = "유저 알림 상세 조회")
  public ApiResult<UserNotificationResponse> retrieve(
      @AuthenticationPrincipal UserInfo userInfo,
      @PathVariable Long notificationId) {
    Long userId = userInfo.getId();
    UserNotificationResponse notification = userNotificationApplicationService.retrieve(userId, notificationId);
    return ApiResult.ok(notification);
  }

  @PostMapping("/read")
  @Operation(summary = "유저 알림 읽음 상태 변경")
  public ApiResult<Void> markAsRead(
      @AuthenticationPrincipal UserInfo userInfo) {
    Long userId = userInfo.getId();
    userNotificationApplicationService.markAsRead(userId);
    return ApiResult.ok();
  }
}
