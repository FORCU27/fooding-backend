package im.fooding.core.service.user;

import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.model.user.UserAuthority;
import im.fooding.core.repository.user.UserAuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthorityService {
    private final UserAuthorityRepository repository;

    public void create(User user, Role role) {
        UserAuthority userAuthority = UserAuthority.builder()
                .user(user)
                .role(role)
                .build();
        repository.save(userAuthority);
    }
}
