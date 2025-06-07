package im.fooding.core.global;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByUserId(long id) {
        UserInfo user = userRepository.findById(id)
                .map(UserInfo::new)
                .orElseThrow(() -> new ApiException(ErrorCode.ACCESS_DENIED_EXCEPTION));
        return user;
    }
}
