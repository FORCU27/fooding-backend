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

    @GetMapping("/me")
    public PageResponse<AppStoreResponse> getMyStores(
            @AuthenticationPrincipal UserInfo userInfo,
            @ModelAttribute BasicSearch basicSearch
    ) {
        return appStoreService.getMyStores(userInfo.getId(), basicSearch);
    }
}
