package im.fooding.app.service.admin.user;

import im.fooding.app.dto.request.admin.user.AdminCreateUserRequest;
import im.fooding.app.dto.request.admin.user.AdminSearchUserRequest;
import im.fooding.app.dto.request.admin.user.AdminUpdateUserRequest;
import im.fooding.app.dto.response.admin.user.AdminUserResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.user.UserAuthorityService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserService userService;
    private final UserAuthorityService userAuthorityService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public PageResponse<AdminUserResponse> list(AdminSearchUserRequest search) {
        Page<User> page = userService.list(search.getSearchString(), search.getPageable(), search.getRole());
        return PageResponse.of(
                page.getContent().stream().map(AdminUserResponse::from).toList(),
                PageInfo.of(page));
    }

    @Transactional(readOnly = true)
    public AdminUserResponse retrieve(long id) {
        User user = userService.findById(id);
        return AdminUserResponse.from(user);
    }

    @Transactional
    public Long create(AdminCreateUserRequest request) {
        Role role = request.getRole();
        if (Role.USER == role) {
            throw new ApiException(ErrorCode.SOCIAL_LOGIN_ONLY);
        }
        User user = userService.create(request.getEmail(), request.getNickname(),
                passwordEncoder.encode(request.getPassword()), request.getPhoneNumber(), request.getGender(), request.getName(), "");
        userAuthorityService.create(user, request.getRole());
        return user.getId();
    }

    @Transactional
    public void update(long id, AdminUpdateUserRequest request) {
        userService.update(id, request.getNickname(), request.getPhoneNumber(), request.getGender(), null, false, "");
    }

    @Transactional
    public void delete(long id, long deletedBy) {
        userService.delete(id, deletedBy);
    }
}
