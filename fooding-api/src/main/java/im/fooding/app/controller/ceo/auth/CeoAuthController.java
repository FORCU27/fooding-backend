package im.fooding.app.controller.ceo.auth;

import im.fooding.app.service.auth.AuthService;
import im.fooding.core.common.ApiResult;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping( "/api/v1/ceo/auth")
@RequiredArgsConstructor
public class CeoAuthController {
    private final AuthService authService;

    @GetMapping( "/find/email" )
    public ApiResult<Map<String, String>> findEmail(
            @RequestParam String name,
            @RequestParam String phoneNumber
    ){
        String email = authService.findUserEmail( name, phoneNumber);
        return ApiResult.ok( Map.of( "email", email ) );
    }

    @GetMapping( "/find/password" )
    public ApiResult<Void> findPassword(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String phoneNumber
    ){
        authService.findUserPassword( email, name, phoneNumber);
        return ApiResult.ok();
    }
}
