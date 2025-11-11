package im.fooding.app.controller.admin.storeNotification;

import im.fooding.app.dto.request.admin.storeNotification.AdminStoreNotificationPageRequest;
import im.fooding.app.dto.response.admin.storeNotification.AdminStoreNotificationResponse;
import im.fooding.app.service.admin.storeNotification.AdminStoreNotificationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
