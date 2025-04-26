package im.fooding.app.controller.ceo.storepost;

import im.fooding.app.dto.response.ceo.storepost.StorePostResponse;
import im.fooding.app.service.ceo.storepost.CeoStorePostService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/storeposts")
@Tag(name = "CeoStorePostController", description = "점주 소식 컨트롤러")
@Slf4j
public class CeoStorePostController {
    private final CeoStorePostService ceoStorePostService;

    @GetMapping
    @Operation(summary = "특정 가게 소식 전체 조회")
    public ApiResult<List<StorePostResponse>> list(@RequestParam Long storeId) {
      List<StorePostResponse> storePosts = ceoStorePostService.getStorePosts(storeId);
      return ApiResult.ok(storePosts);
    }
}
