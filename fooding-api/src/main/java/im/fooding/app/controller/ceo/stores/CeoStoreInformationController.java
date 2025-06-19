package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.request.ceo.store.information.CeoCreateStoreInformationRequest;
import im.fooding.app.dto.request.ceo.store.information.CeoUpdateStoreInformationRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreInformationResponse;
import im.fooding.app.service.ceo.store.CeoStoreInformationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/stores")
@Tag(name = "CeoStoreInformationController", description = "Ceo Store 부가정보 컨트롤러")
public class CeoStoreInformationController {
    private final CeoStoreInformationService service;

    @PostMapping("/{storeId}/information")
    @Operation(summary = "부가정보 등록")
    public ApiResult<Long> create(@PathVariable Long storeId, @Valid @RequestBody CeoCreateStoreInformationRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.create(storeId, request, userInfo.getId()));
    }

    @PutMapping("/{storeId}/information/{id}")
    @Operation(summary = "부가정보 수정")
    public ApiResult<Long> update(@PathVariable Long storeId, @PathVariable Long id, @Valid @RequestBody CeoUpdateStoreInformationRequest request, @AuthenticationPrincipal UserInfo userInfo) {
        service.update(storeId, id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @GetMapping("/{storeId}/information")
    @Operation(summary = "부가정보 조회")
    public ApiResult<CeoStoreInformationResponse> retrieve(@PathVariable Long storeId, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(storeId, userInfo.getId()));
    }

    @DeleteMapping("/{storeId}/information/{id}")
    @Operation(summary = "부가정보 삭제")
    public ApiResult<Void> delete(@PathVariable Long storeId, @PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}
