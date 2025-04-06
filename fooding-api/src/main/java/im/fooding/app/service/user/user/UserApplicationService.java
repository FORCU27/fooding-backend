package im.fooding.app.service.user.user;

import im.fooding.app.dto.response.admin.manager.AdminManagerResponse;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserApplicationService {
    private final UserService userService;

    /**
     * user id로 조회
     *
     * @param id
     * @return ResponseUserDto
     */
    @Transactional(readOnly = true)
    public AdminManagerResponse retrieve(long id) {
        return AdminManagerResponse.of(userService.findById(id));
    }
}
