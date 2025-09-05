package im.fooding.app.controller.user.banner;


import im.fooding.app.dto.request.user.banner.UserBannerPageRequest;
import im.fooding.app.dto.response.user.banner.UserBannerResponse;
import im.fooding.app.service.user.banner.UserBannerService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/banners")
@Tag(name = "UserBannerController", description = "User Banner Controller")
public class UserBannerController {

    private final UserBannerService userBannerService;

    @GetMapping("/{id}")
    @Operation(summary = "배너 조회")
    public ApiResult<UserBannerResponse> getBanner(
            @PathVariable String id
    ) {
        return ApiResult.ok(userBannerService.getBanner(id));
    }

    @GetMapping
    @Operation(summary = "배너 조회(page)")
    public ApiResult<PageResponse<UserBannerResponse>> getBanners(
            @Valid UserBannerPageRequest request
    ) {
        return ApiResult.ok(userBannerService.getBanners(request));
    }
}
