package im.fooding.app.controller.ceo.menu;

import im.fooding.app.dto.response.ceo.menu.CeoMenuBoardResponse;
import im.fooding.app.service.ceo.menu.CeoMenuBoardService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/stores/{storeId}/menu-boards")
@Tag(name = "CeoMenuBoardController", description = "점주 메뉴판 컨트롤러")
public class CeoMenuBoardController {

    private final CeoMenuBoardService ceoMenuBoardService;

    @PostMapping
    @Operation(summary = "메뉴판 생성")
    public ApiResult<Void> create(
            @PathVariable long storeId,
            @RequestBody @Valid CeoMenuBoardCreateRequest request
    ) {
        ceoMenuBoardService.create(storeId, request);
        return ApiResult.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "메뉴판 조회")
    public ApiResult<CeoMenuBoardResponse> get(
            @PathVariable long id
    ) {
        return ApiResult.ok(ceoMenuBoardService.get(id));
    }

    @GetMapping
    @Operation(summary = "메뉴판 조회(list)")
    public ApiResult<PageResponse<CeoMenuBoardResponse>> list(
            @PathVariable long storeId,
            @Valid CeoMenuBoardListRequest request
    ) {
        return ApiResult.ok(ceoMenuBoardService.list(storeId, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "메뉴판 제거")
    public ApiResult<Void> delete(
            @AuthenticationPrincipal UserInfo userInfo,
            @PathVariable long id
    ) {
        ceoMenuBoardService.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
