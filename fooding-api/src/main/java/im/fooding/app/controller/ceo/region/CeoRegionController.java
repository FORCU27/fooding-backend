package im.fooding.app.controller.ceo.region;

import im.fooding.app.dto.request.ceo.region.CeoRegionListRequest;
import im.fooding.app.dto.response.ceo.region.CeoRegionResponse;
import im.fooding.app.service.ceo.region.CeoRegionService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/regions")
@Tag(name = "CeoRegionController", description = "CEO 지역 컨트롤러")
public class CeoRegionController {

    private final CeoRegionService ceoRegionService;

    @GetMapping
    @Operation(summary = "지역 조회(page)")
    public ApiResult<PageResponse<CeoRegionResponse>> list(
            @Valid CeoRegionListRequest request
    ) {
        return ApiResult.ok(ceoRegionService.list(request));
    }
}
