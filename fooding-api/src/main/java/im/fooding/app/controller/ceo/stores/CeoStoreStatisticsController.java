package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.request.ceo.store.CeoStoreStatisticsRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreStatisticsResponse;
import im.fooding.app.service.ceo.store.CeoStoreStatisticsService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/stores")
@Tag(name = "CeoStoreStatisticsController", description = "[CEO] 가계 통계 컨트롤러")
public class CeoStoreStatisticsController {

    private final CeoStoreStatisticsService service;

    @GetMapping("/{storeId}/statistics")
    @Operation(summary = "가계별 통계 조회")
    public ApiResult<CeoStoreStatisticsResponse> retrieve(
            @PathVariable long storeId,
            @ModelAttribute CeoStoreStatisticsRequest request
    ) {
        return ApiResult.ok(service.retrieve(storeId, request));
    }
}
