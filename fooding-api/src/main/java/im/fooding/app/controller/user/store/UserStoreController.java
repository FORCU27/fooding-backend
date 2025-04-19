package im.fooding.app.controller.user.store;

import im.fooding.app.dto.request.user.store.UserRetrieveStoreRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.app.dto.response.user.store.StoreResponse;
import im.fooding.app.service.user.store.StoreApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/user/stores")
@Tag(name = "User Store Controller", description = "유저 스토어 컨트롤러")
public class UserStoreController {

    private final StoreApplicationService service;

    @GetMapping
    @Operation(summary = "가게 목록 조회 - [정렬기준 : 리뷰순 / 등록순]")
    public ApiResult<PageResponse<StoreResponse>> list(
            @Valid @RequestBody UserRetrieveStoreRequest request
    ) {
        return ApiResult.ok(service.list(request));
    }
}
