package im.fooding.app.controller.place;

import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.app.service.place.PlaceStoreService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place/stores")
@Tag(name = "PlaceStoreController", description = "플레이스 가게 조회 컨트롤러")
public class PlaceStoreController {

    private final PlaceStoreService placeStoreService;

    @GetMapping("/{id}")
    @Operation(summary = "플레이스 가게 상세 조회")
    public ApiResult<UserStoreResponse> retrieve(@PathVariable Long id,
                                                 @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(placeStoreService.retrieve(id, userInfo));
    }
}
