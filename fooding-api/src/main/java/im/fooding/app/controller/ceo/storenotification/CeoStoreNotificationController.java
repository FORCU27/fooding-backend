package im.fooding.app.controller.ceo.storenotification;

import im.fooding.app.dto.response.ceo.storenotification.CeoStoreNotificationResponse;
import im.fooding.app.service.ceo.storenotification.CeoStoreNotificationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/store-notifications")
@Tag(name = "CeoStoreNotificationController", description = "점주 가게 알림 컨트롤러")
@Slf4j
public class CeoStoreNotificationController {
    private final CeoStoreNotificationService ceoStoreNotificationService;

    @GetMapping
    @Operation(summary = "점주 가게 알림 전체 조회(전체/카테고리별)")
    public ApiResult<PageResponse<CeoStoreNotificationResponse>> list(
            @RequestParam Long storeId,
            @RequestParam(required = false) String category,
            @ModelAttribute BasicSearch search
    ) {
      Pageable pageable = search.getPageable();

      PageResponse<CeoStoreNotificationResponse> response =
              (category == null || category.trim().isEmpty())
                      ? ceoStoreNotificationService.list(storeId, pageable)
                      : ceoStoreNotificationService.listByCategory(storeId, category, pageable);

      return ApiResult.ok(response);
    }
}
