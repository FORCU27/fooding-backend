package im.fooding.core.service.user;

import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.global.jwt.service.JwtService;
import im.fooding.core.global.util.Util;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveManager(String email, String password) {
        checkDuplicateEmail(email, Role.ADMIN);
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public TokenResponse loginManager(String email, String password) {
        User manager = findByEmailAndRole(email, Role.ADMIN);
        if (!passwordEncoder.matches(password, manager.getPassword())) {
            throw new ApiException(ErrorCode.LOGIN_FAILED);
        }
        TokenResponse tokenResponse = jwtService.issueJwtToken(manager.getId());
        manager.updateRefreshToken(tokenResponse.getRefreshToken());
        return tokenResponse;
    }

    @Transactional(readOnly = true)
    public User me() {
        return userRepository.findById(Util.getUserInfo().getId()).orElseThrow(() -> new ApiException(ErrorCode.MANAGER_NOT_FOUND));
    }

    private User findByEmailAndRole(String email, Role role) {
        return userRepository.findByEmailAndRole(email, role)
                .orElseThrow(() -> new ApiException(ErrorCode.MANAGER_NOT_FOUND));
    }

    private void checkDuplicateEmail(String email, Role role) {
        userRepository.findByEmailAndRole(email, role).ifPresent(it -> {
            throw new ApiException(ErrorCode.DUPLICATED_REGISTER_EMAIL);
        });
    }
}
