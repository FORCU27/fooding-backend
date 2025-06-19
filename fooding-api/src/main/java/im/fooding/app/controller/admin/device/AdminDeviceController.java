package im.fooding.app.controller.admin.device;

import im.fooding.app.dto.request.user.device.RetrieveDeviceRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.app.service.app.device.AppDeviceService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/devices")
@Tag(name = "Admin Device Controller", description = "관리자 디바이스 컨트롤러")
public class AdminDeviceController {
    private final AppDeviceService service;

    @GetMapping()
    @Operation(summary = "해당 매장의 모든 디바이스 조회")
    public ApiResult<PageResponse<StoreDeviceResponse>> list(
            @ModelAttribute RetrieveDeviceRequest request
    ) {
        return ApiResult.ok(service.list("Admin", request));
    }

    @GetMapping("/all")
    @Operation(summary = "모든 디바이스 조회")
    public ApiResult<PageResponse<StoreDeviceResponse>> listAll(
            @AuthenticationPrincipal long userId
    ) {
        RetrieveDeviceRequest request = RetrieveDeviceRequest.builder().build();
        return ApiResult.ok(service.list("Admin", request ));
    }
}
