package im.fooding.app.service.app.user;

import im.fooding.app.dto.response.app.user.AppUserResponse;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final UserService userService;

    public AppUserResponse getUser(Long id) {
        return AppUserResponse.from(userService.findById(id));
    }
}
