package im.fooding.app.controller.user.device;

import im.fooding.app.dto.request.user.device.RetrieveStoreDeviceRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.app.service.user.device.DeviceApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/devices")
@Tag(name = "User Device Controller", description = "사용자 디바이스 컨트롤러")
public class UserDeviceController {
    private final DeviceApplicationService service;

    @GetMapping("")
    @Operation(summary="가게 소속 디바이스 조회")
    public ApiResult<PageResponse<StoreDeviceResponse>> retrieve(
            @Valid RetrieveStoreDeviceRequest request
    ) {
        return ApiResult.ok(service.retrieveStoreDevice( request ));
    }
}
