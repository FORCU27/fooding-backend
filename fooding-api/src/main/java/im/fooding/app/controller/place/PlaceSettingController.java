package im.fooding.app.controller.place;

import im.fooding.app.dto.response.place.PlaceSettingResponse;
import im.fooding.app.service.place.PlaceSettingQueryService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place/stores/{storeId}/settings")
@Tag(name = "PlaceSettingController", description = "플레이스 설정 조회 컨트롤러")
public class PlaceSettingController {

    private final PlaceSettingQueryService placeSettingQueryService;

    @GetMapping
    @Operation(summary = "플레이스 설정 조회")
    public ApiResult<List<PlaceSettingResponse>> list(@PathVariable Long storeId) {
        return ApiResult.ok(placeSettingQueryService.list(storeId));
    }
}
