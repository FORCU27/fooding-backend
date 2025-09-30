package im.fooding.app.controller.ceo.auth;

import im.fooding.app.dto.response.ceo.auth.CeoAuthenticatePhoneResponse;
import im.fooding.app.service.auth.AuthService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping( "/api/v1/ceo/auth")
@RequiredArgsConstructor
@Tag( name = "CeoAuthControlelr", description = "CEO 인증 및 계정 관련 컨트롤러" )
public class CeoAuthController {
    private final AuthService authService;

    // 휴대폰 인증
    @PostMapping( "/verify/phone" )
    @Operation(summary = "휴대폰 인증 진행", description = "휴대폰으로 6자리의 int타입 인증 코드를 전송한다.")
    public ApiResult<Void> sendAuthenticationCode(
            @RequestParam String name,
            @RequestParam String phoneNumber
    ){
        authService.sendAuthenticateCode( name, phoneNumber );
        return ApiResult.ok();
    }

    // 인증번호 확인
    @GetMapping( "/verify/phone" )
    @Operation(summary = "휴대폰 인증 확인", description = "SMS로 받은 6자리 int 타입의 인증 코드가 올바른지 확인한다.")
    public ApiResult<CeoAuthenticatePhoneResponse> checkAuthenticationCode(
            @RequestParam String phoneNumber,
            @RequestParam int code
    ){
        return ApiResult.ok( authService.isCorrectCode( phoneNumber, code ) );
    }

    // 이메일(아이디) 찾기 결과 전달
    @GetMapping( "/find/email" )
    @Operation(summary = "아이디 찾기 결과 전달", description = "사용자의 이메일(아이디) 찾기 조회 결과를 전달한다.")
    public ApiResult<Map<String, String>> findEmail(
            @RequestParam String phoneNumber,
            @RequestParam int code
    ){
        return ApiResult.ok( Map.of( "email", authService.getEmail( phoneNumber, code ) ) );
    }

    // 이메일을 통한 재설정 링크 전달
    @Operation(summary = "이메일을 통한 비밀번호 재설정 주소 전달", description = "사용자의 이메일로 비밀번호 재설정 링크를 전달한다.")
    @GetMapping( "/find/password/email" )
    public ApiResult<Void> getLinkByEmail(
            @RequestParam String name,
            @RequestParam String phoneNumber,
            @RequestParam int code
    ){
        authService.sendPasswordResetUrl( name, phoneNumber, code, true );
        return ApiResult.ok();
    }

    // SMS를 통한 재설정 링크 전달
    @GetMapping( "/find/password/sms" )
    @Operation(summary = "SMS를 통한 비밀번호 재설정 주소 전달", description = "사용자의 SMS로 비밀번호 재설정 링크를 전달한다.")
    public ApiResult<Void> getLinkBySms(
            @RequestParam String name,
            @RequestParam String phoneNumber,
            @RequestParam int code
    ){
        authService.sendPasswordResetUrl( name, phoneNumber, code, false );
        return ApiResult.ok();
    }

    // 비밀번호 재설정
    @PostMapping( "/reset/password/{encodedLine}" )
    @Operation(summary = "비밀번호 변경", description = "인코딩 된 정보를 토대로 비밀번호를 변경한다.")
    public ApiResult<Void> resetPassword(
            @PathVariable String encodedLine,
            @RequestParam String password
    ){
        authService.resetPassword( encodedLine, password );
        return ApiResult.ok();
    }
}
