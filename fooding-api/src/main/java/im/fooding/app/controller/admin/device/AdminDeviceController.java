package im.fooding.app.controller.admin.device;

import im.fooding.app.dto.request.admin.device.RetrieveAllDeviceRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.app.service.user.device.DeviceApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/devices")
@Tag(name="Admin Device Controller", description = "관리자 디바이스 컨트롤러")
public class AdminDeviceController {
    private final DeviceApplicationService service;

    @GetMapping()
    @Operation(summary="모든 디바이스 조회")
    public ApiResult<PageResponse<StoreDeviceResponse>> list(
            @RequestParam RetrieveAllDeviceRequest request
            ){
        return ApiResult.ok(service.retrieveAllDevice( request ) );
    }
}
