package im.fooding.core.global;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.UserAuthority;
import im.fooding.core.repository.user.UserAuthorityRepository;
import im.fooding.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService {
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    public UserDetails loadUserByUserId(long id) {
        UserInfo user = userRepository.findById(id)
                .map(UserInfo::new)
                .orElseThrow(() -> new ApiException(ErrorCode.ACCESS_DENIED_EXCEPTION));
        initAuthority(user);
        return user;
    }

    private void initAuthority(UserInfo userInfo) {
        List<UserAuthority> authorities = userAuthorityRepository.findAllByUserId(userInfo.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.addAll(authorities.stream().map(it -> new SimpleGrantedAuthority(it.getRole().getValue())).toList());
        userInfo.setAuthorities(grantedAuthorities);
    }
}
