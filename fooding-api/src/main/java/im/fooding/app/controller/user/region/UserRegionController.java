package im.fooding.app.controller.user.region;

import im.fooding.app.dto.request.user.region.UserRegionListRequest;
import im.fooding.app.dto.response.user.region.UserRegionResponse;
import im.fooding.app.service.user.region.UserRegionService;
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
@RequestMapping("/user/regions")
@Tag(name = "UserRegionController", description = "User 지역 컨트롤러")
public class UserRegionController {

    private final UserRegionService userRegionService;

    @GetMapping
    @Operation(summary = "지역 조회(page)")
    public ApiResult<PageResponse<UserRegionResponse>> list(
            @Valid UserRegionListRequest request
    ) {
        return ApiResult.ok(userRegionService.list(request));
    }
}
