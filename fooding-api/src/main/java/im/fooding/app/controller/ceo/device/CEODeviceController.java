package im.fooding.app.controller.ceo.device;

import im.fooding.app.dto.request.user.device.ConnectDeviceRequest;
import im.fooding.app.dto.request.user.device.RetrieveDeviceRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.app.service.app.device.AppDeviceService;
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

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/devices")
@Tag(name = "CEO Device Controller", description = "CEO 디바이스 컨트롤러")
public class CEODeviceController {
    private final AppDeviceService service;

    @GetMapping
    @Operation(summary = "가게 소속 디바이스 조회")
    public ApiResult<PageResponse<StoreDeviceResponse>> retrieve(
            @ModelAttribute RetrieveDeviceRequest request
    ) {
        return ApiResult.ok(service.list("CEO", request));
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
}
