package im.fooding.app.controller.user.menu;

import im.fooding.app.dto.response.user.menu.UserMenuResponse;
import im.fooding.app.service.user.menu.UserMenuService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/stores")
@Tag(name = "UserMenuController", description = "유저 메뉴 컨트롤러")
public class UserMenuController {

    private final UserMenuService userMenuService;

    @GetMapping("/{storeId}/menus")
    public ApiResult<List<UserMenuResponse>> list(
            @PathVariable Long storeId
    ) {
        List<UserMenuResponse> menus = userMenuService.list(storeId);
        return ApiResult.ok(menus);
    }
}
