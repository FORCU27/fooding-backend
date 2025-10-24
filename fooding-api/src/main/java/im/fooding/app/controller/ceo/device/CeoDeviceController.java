package im.fooding.app.controller.ceo.device;

import im.fooding.app.dto.request.ceo.device.GetDeviceLogsRequest;
import im.fooding.app.dto.request.user.device.ConnectDeviceRequest;
import im.fooding.app.dto.request.user.device.RetrieveDeviceRequest;
import im.fooding.app.dto.response.ceo.device.GetDeviceLogsResponse;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.app.service.app.device.AppDeviceService;
import im.fooding.app.service.ceo.device.CeoDeviceService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.model.device.ServiceType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/devices")
@Tag(name = "CEO Device Controller", description = "CEO 디바이스 컨트롤러")
public class CeoDeviceController {
    private final AppDeviceService service;
    private final CeoDeviceService ceoDeviceService;

    @GetMapping
    @Operation(summary = "가게 소속 디바이스 조회")
    public ApiResult<PageResponse<StoreDeviceResponse>> retrieve(
            @ModelAttribute RetrieveDeviceRequest request
    ) {
        return ApiResult.ok(service.list( request));
    }

    @PostMapping
    @Operation(summary = "로그인 유저 디바이스 연결")
    public ApiResult<Void> connect(
            @AuthenticationPrincipal UserInfo userInfo,
            @Valid @RequestBody ConnectDeviceRequest request
    ) {
        service.connect(request, Optional.ofNullable(userInfo).map(UserInfo::getId).orElse(null));
        return ApiResult.ok();
    }

    @PostMapping("/{deviceId}/service")
    @Operation(summary = "디바이스의 서비스 변경")
    public ApiResult<Void> changeDeviceService(
            @RequestParam ServiceType serviceType,
            @PathVariable Long deviceId
    ){
        ceoDeviceService.updateDeviceServiceType( deviceId, serviceType);
        return ApiResult.ok();
    }

    @PostMapping("/{deviceId}/user/disconnect" )
    @Operation(summary = "디바이스와 연결된 사용자 제거")
    public ApiResult<Void> disconnectUser(
            @PathVariable Long deviceId,
            @AuthenticationPrincipal UserInfo userInfo
    ){
        ceoDeviceService.disconnectWithUser( deviceId, userInfo.getId() );
        return ApiResult.ok();
    }

    @GetMapping( "/logs" )
    @Operation(summary = "해당 디바이스와 관련된 로그 조회")
    public ApiResult<PageResponse<GetDeviceLogsResponse>> retrieveLogs(
            @ModelAttribute GetDeviceLogsRequest request,
            @AuthenticationPrincipal UserInfo userInfo
    ){
        return ApiResult.ok( ceoDeviceService.retrieveLogs(request, Optional.ofNullable(userInfo).map(UserInfo::getId).orElse(null)) );
    }
}
