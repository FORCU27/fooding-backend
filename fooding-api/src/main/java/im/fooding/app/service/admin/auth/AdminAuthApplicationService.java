package im.fooding.app.service.admin.auth;

import im.fooding.app.dto.request.admin.auth.AdminLoginRequest;
import im.fooding.app.dto.request.admin.manager.AdminCreateManagerRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.global.jwt.service.JwtService;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAuthApplicationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 관리자 계정 생성
     *
     * @param adminCreateManagerRequest
     */
    @Transactional
    public void register(AdminCreateManagerRequest adminCreateManagerRequest) {
        userService.save(
                adminCreateManagerRequest.getEmail(),
                adminCreateManagerRequest.getNickname(),
                passwordEncoder.encode(adminCreateManagerRequest.getPassword()),
                Role.ADMIN
        );
    }

    /**
     * 관리자 로그인
     *
     * @param loginManagerRequest
     * @return TokenResponse
     */
    @Transactional
    public TokenResponse login(AdminLoginRequest loginManagerRequest) {
        User manager = userService.findByEmail(loginManagerRequest.getEmail());
        if (!passwordEncoder.matches(loginManagerRequest.getPassword(), manager.getPassword())) {
            throw new ApiException(ErrorCode.LOGIN_FAILED);
        }
        TokenResponse tokenResponse = jwtService.issueJwtToken(manager.getId());
        manager.updatedRefreshToken(tokenResponse.getRefreshToken());
        return tokenResponse;
    }
}
