package im.fooding.app.controller.ceo.users;

import im.fooding.app.dto.response.user.UserResponse;
import im.fooding.app.service.ceo.user.CeoUserService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/users")
@Tag(name = "CeoUserController", description = "Ceo 유저 컨트롤러")
public class CeoUserController {
    private final CeoUserService service;

    @GetMapping
    @Operation(summary = "유저 목록 조회")
    public ApiResult<PageResponse<UserResponse>> list(@Valid BasicSearch search, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(search, userInfo.getId()));
    }
}
