package im.fooding.app.controller.app.device;

import im.fooding.app.dto.request.user.device.ConnectDeviceRequest;
import im.fooding.app.service.user.device.DeviceApplicationService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/devices")
public class AppDeviceController {
    private final DeviceApplicationService service;

    @GetMapping( "/connect" )
    @Operation(summary="비로그인 유저 디바이스 연결")
    public ApiResult<Void> connect(
            @Valid @RequestBody ConnectDeviceRequest request
    ){
        service.connect( request );
        return ApiResult.ok();
    }


}
