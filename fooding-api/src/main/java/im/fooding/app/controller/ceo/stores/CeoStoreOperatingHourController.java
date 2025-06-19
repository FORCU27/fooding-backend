package im.fooding.app.controller.ceo.stores;

import im.fooding.app.dto.request.ceo.store.information.CeoCreateStoreOperatingHourRequest;
import im.fooding.app.dto.request.ceo.store.information.CeoUpdateStoreOperatingHourRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreOperatingHourResponse;
import im.fooding.app.service.ceo.store.CeoStoreOperatingHourService;
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
@Tag(name = "CeoStoreOperatingHourController", description = "Ceo Store 영업시간/휴무일 컨트롤러")
public class CeoStoreOperatingHourController {
    private final CeoStoreOperatingHourService service;

    @PostMapping("/{storeId}/operating-hour")
    @Operation(summary = "영업시간/휴무일 등록")
    public ApiResult<Long> create(@PathVariable Long storeId, @Valid @RequestBody CeoCreateStoreOperatingHourRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.create(storeId, request, userInfo.getId()));
    }

    @PutMapping("/{storeId}/operating-hour/{id}")
    @Operation(summary = "영업시간/휴무일 등록 수정")
    public ApiResult<Long> update(@PathVariable Long storeId, @PathVariable Long id, @Valid @RequestBody CeoUpdateStoreOperatingHourRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        service.update(storeId, id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @GetMapping("/{storeId}/operating-hour")
    @Operation(summary = "영업시간/휴무일 조회")
    public ApiResult<CeoStoreOperatingHourResponse> retrieve(@PathVariable Long storeId, @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(storeId, userInfo.getId()));
    }

    @DeleteMapping("/{storeId}/operating-hour/{id}")
    @Operation(summary = "영업시간/휴무일 삭제")
    public ApiResult<Void> delete(@PathVariable Long storeId, @PathVariable Long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(storeId, id, userInfo.getId());
        return ApiResult.ok();
    }
}
