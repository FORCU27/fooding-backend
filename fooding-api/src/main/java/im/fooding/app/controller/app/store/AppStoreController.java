package im.fooding.app.controller.app.store;

import im.fooding.app.dto.response.app.store.AppStoreResponse;
import im.fooding.app.service.app.store.AppStoreService;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/stores")
@Tag(name="App Store Controller", description = "[APP] 가게 컨트롤")
@Slf4j
public class AppStoreController {
    
    private final AppStoreService appStoreService;

    @GetMapping
    @Operation(summary = "본인 가게 페이징 조회")
    public PageResponse<AppStoreResponse> getMyStores(
            @AuthenticationPrincipal UserInfo userInfo,
            @ModelAttribute BasicSearch basicSearch
    ) {
        // TODO: 유저, 권한 개발 후 자신이 접근할 수 있는 Store 만 조회할 수 있도록 수정한다
        return appStoreService.getMyStores(1L, basicSearch);
    }
}
