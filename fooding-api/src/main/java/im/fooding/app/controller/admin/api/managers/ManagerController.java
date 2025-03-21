package im.fooding.app.controller.admin.api.managers;

import im.fooding.app.controller.admin.api.managers.dto.CreateManagerDto;
import im.fooding.app.controller.admin.api.managers.dto.LoginManagerDto;
import im.fooding.app.controller.admin.api.managers.dto.UpdateMangerDto;
import im.fooding.app.service.manager.ManagerApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.user.ResponseUserDto;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.jwt.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/managers")
@Tag(name = "ManagerController", description = "관리자 매니저 컨트롤러")
@Slf4j
public class ManagerController {
    private final ManagerApplicationService service;

    @PostMapping
    @Operation(summary = "관리자 회원가입")
    public ApiResult<Void> register(@RequestBody @Valid CreateManagerDto createManagerDto) {
        service.register(createManagerDto);
        return ApiResult.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "관리자 로그인")
    public ApiResult<TokenResponse> login(@Valid @RequestBody LoginManagerDto loginManagerDto) {
        return ApiResult.ok(service.login(loginManagerDto));
    }

    @GetMapping("/me")
    @Operation(summary = "로그인한 정보")
    public ApiResult<ResponseUserDto> me(@AuthenticationPrincipal UserInfo userInfo) {
        return ApiResult.ok(service.retrieve(userInfo.getId()));
    }

    @GetMapping
    @Operation(summary = "관리자 리스트")
    public ApiResult<PageResponse> list(BasicSearch search) {
        return ApiResult.ok(service.list(search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "관리자 조회")
    public ApiResult<ResponseUserDto> retrieve(@PathVariable long id) {
        return ApiResult.ok(service.retrieve(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "관리자 수정")
    public ApiResult<Void> update(@PathVariable long id, @RequestBody @Valid UpdateMangerDto updateMangerDto) {
        service.update(id, updateMangerDto);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "관리자 삭제")
    public ApiResult<Void> delete(@PathVariable long id, @AuthenticationPrincipal UserInfo userInfo) {
        service.delete(id, userInfo.getId());
        return ApiResult.ok();
    }
}
