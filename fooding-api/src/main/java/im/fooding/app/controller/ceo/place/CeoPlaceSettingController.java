package im.fooding.app.controller.ceo.place;

import im.fooding.app.dto.request.ceo.place.CeoPlaceSettingCreateRequest;
import im.fooding.app.dto.request.ceo.place.CeoPlaceSettingUpdateRequest;
import im.fooding.app.dto.response.ceo.place.CeoPlaceSettingResponse;
import im.fooding.app.service.ceo.place.CeoPlaceSettingService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/place-settings")
@Tag(name = "CeoPlaceSettingController", description = "사장님 플레이스 설정 컨트롤러")
public class CeoPlaceSettingController {

    private final CeoPlaceSettingService ceoPlaceSettingService;

    @PostMapping
    @Operation(summary = "플레이스 설정 생성")
    public ApiResult<Long> create(@Valid @RequestBody CeoPlaceSettingCreateRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(ceoPlaceSettingService.create(request, userInfo.getId()));
    }

    @GetMapping
    @Operation(summary = "가게별 플레이스 설정 목록 조회")
    public ApiResult<List<CeoPlaceSettingResponse>> list(@RequestParam Long storeId,
                                                         @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(ceoPlaceSettingService.list(storeId, userInfo.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "플레이스 설정 상세 조회")
    public ApiResult<CeoPlaceSettingResponse> retrieve(@PathVariable Long id,
                                                       @AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(ceoPlaceSettingService.retrieve(id, userInfo.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "플레이스 설정 수정")
    public ApiResult<Void> update(@PathVariable Long id,
                                  @Valid @RequestBody CeoPlaceSettingUpdateRequest request,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        ceoPlaceSettingService.update(id, request, userInfo.getId());
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "플레이스 설정 삭제")
    public ApiResult<Void> delete(@PathVariable Long id,
                                  @AuthenticationPrincipal UserInfo userInfo) {
        ceoPlaceSettingService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
