package im.fooding.app.controller.app.store;

import im.fooding.app.dto.response.app.store.AppStoreResponse;
import im.fooding.app.service.app.store.AppStoreService;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/stores")
public class AppStoreController {
    
    private final AppStoreService appStoreService;

    @GetMapping
    public PageResponse<AppStoreResponse> getMyStores(
            @AuthenticationPrincipal UserInfo userInfo,
            @ModelAttribute BasicSearch basicSearch
    ) {
        // TODO: 유저, 권한 개발 후 자신이 접근할 수 있는 Store 만 조회할 수 있도록 수정한다
        return appStoreService.getMyStores(1L, basicSearch);
    }
}
