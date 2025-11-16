package im.fooding.app.controller.ceo.storepost;

import im.fooding.app.dto.request.ceo.storepost.CeoCreateStorePostRequest;
import im.fooding.app.dto.request.ceo.storepost.CeoSearchStorePostRequest;
import im.fooding.app.dto.request.ceo.storepost.CeoUpdateStorePostRequest;
import im.fooding.app.dto.response.ceo.storepost.CeoStorePostResponse;
import im.fooding.app.service.ceo.storepost.CeoStorePostService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
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
    public ApiResult<PageResponse<CeoStorePostResponse>> list(@Valid CeoSearchStorePostRequest search, @AuthenticationPrincipal UserInfo userInfo) {
      return ApiResult.ok(ceoStorePostService.list(search, userInfo.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 가게 소식 상세조회")
    public ApiResult<CeoStorePostResponse> retrieve(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(ceoStorePostService.retrieve(id, userInfo.getId()));
    }

    @PostMapping
    @Operation(summary = "소식 생성")
    public ApiResult<Long> create(@RequestBody @Valid CeoCreateStorePostRequest request, @AuthenticationPrincipal UserInfo userInfo) {
      Long id = ceoStorePostService.create(request, userInfo.getId());
      return ApiResult.ok(id);
    }

    @PutMapping("/{storePostId}")
    @Operation(summary = "소식 수정")
    public ApiResult<Void> update(@PathVariable Long storePostId,
                                  @RequestBody @Valid CeoUpdateStorePostRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
      ceoStorePostService.update(storePostId, request, userInfo.getId());
      return ApiResult.ok();
    }

    @DeleteMapping("/{storePostId}")
    @Operation(summary = "소식 삭제")
    public ApiResult<Void> delete(@PathVariable Long storePostId, @AuthenticationPrincipal UserInfo userInfo) {
      ceoStorePostService.delete(storePostId, userInfo.getId());
      return ApiResult.ok();
    }

    @PutMapping("/{storePostId}/active")
    @Operation(summary = "소식 공개여부 활성화")
    public ApiResult<Void> active(@PathVariable Long storePostId, @AuthenticationPrincipal UserInfo userInfo) {
        ceoStorePostService.active(storePostId, userInfo.getId());
        return ApiResult.ok();
    }

    @PutMapping("/{storePostId}/inactive")
    @Operation(summary = "소식 공개여부 비활성화")
    public ApiResult<Void> inactive(@PathVariable Long storePostId, @AuthenticationPrincipal UserInfo userInfo) {
        ceoStorePostService.inactive(storePostId, userInfo.getId());
        return ApiResult.ok();
    }
}
