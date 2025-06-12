package im.fooding.app.controller.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStoreImageRequest;
import im.fooding.app.dto.response.user.store.UserStoreImageResponse;
import im.fooding.app.service.user.store.UserStoreImageService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/store-images")
@Tag(name = "UserStoreImageController", description = "유저 스토어 사진 컨트롤러")
public class UserStoreImageController {
    private final UserStoreImageService service;

    @GetMapping
    @Operation(summary = "사진 리스트 조회")
    public ApiResult<PageResponse<UserStoreImageResponse>> list(@Valid UserSearchStoreImageRequest search) {
        return ApiResult.ok(service.list(search));
    }
}
