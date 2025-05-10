package im.fooding.app.controller.ceo.storepost;

import im.fooding.app.dto.request.ceo.storepost.CeoCreateStorePostRequest;
import im.fooding.app.dto.request.ceo.storepost.CeoUpdateStorePostRequest;
import im.fooding.app.dto.response.ceo.storepost.StorePostResponse;
import im.fooding.app.service.ceo.storepost.CeoStorePostService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/store-posts")
@Tag(name = "CeoStorePostController", description = "점주 소식 컨트롤러")
@Slf4j
public class CeoStorePostController {
    private final CeoStorePostService ceoStorePostService;

    @GetMapping
    @Operation(summary = "특정 가게 소식 전체 조회")
    public ApiResult<List<StorePostResponse>> list(@RequestParam Long storeId) {
      List<StorePostResponse> storePosts = ceoStorePostService.list(storeId);
      return ApiResult.ok(storePosts);
    }

    @PostMapping
    @Operation(summary = "소식 생성")
    public ApiResult<Long> create(@RequestBody @Valid CeoCreateStorePostRequest request) {
      Long id = ceoStorePostService.create(request);
      return ApiResult.ok(id);
    }

    @PutMapping("/{storePostId}")
    @Operation(summary = "소식 수정")
    public ApiResult<Void> update(@PathVariable Long storePostId,
                                  @RequestBody @Valid CeoUpdateStorePostRequest request) {
      ceoStorePostService.update(storePostId, request);
      return ApiResult.ok();
    }

    @DeleteMapping("/{storePostId}")
    @Operation(summary = "소식 삭제")
    public ApiResult<Void> delete(@PathVariable Long storePostId, @AuthenticationPrincipal UserInfo userInfo) {
      ceoStorePostService.delete(storePostId, userInfo.getId());
      return ApiResult.ok();
    }
}
