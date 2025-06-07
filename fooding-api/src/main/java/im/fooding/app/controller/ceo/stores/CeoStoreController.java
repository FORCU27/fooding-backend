package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.request.ceo.store.CeoCreateStoreRequest;
import im.fooding.app.dto.request.ceo.store.CeoUpdateStoreRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreResponse;
import im.fooding.app.service.ceo.store.CeoStoreService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/stores")
@Tag(name = "CeoStoreController", description = "Ceo Store 컨트롤러")
public class CeoStoreController {
    private final CeoStoreService service;

    @GetMapping
    @Operation(summary = "가게 전체 조회")
    public ApiResult<List<CeoStoreResponse>> list(@AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.list(userInfo.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "가게 상세 조회")
    public ApiResult<CeoStoreResponse> retrieve(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(id, userInfo.getId()));
    }

    @PostMapping
    @Operation(summary = "가게 생성")
    public ApiResult<Long> create(@RequestBody @Valid CeoCreateStoreRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.create(request, userInfo.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "가게 수정")
    public ApiResult<Void> update(@PathVariable Long id,
                                  @RequestBody @Valid CeoUpdateStoreRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.update(id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "가게 삭제")
    public ApiResult<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
