package im.fooding.app.service.admin.manager;

import im.fooding.app.dto.request.admin.manager.AdminUpdateMangerRequest;
import im.fooding.app.dto.response.auth.AuthUserResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManagerApplicationService {
    private final UserService userService;

    /**
     * 관리자 리스트 조회
     *
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    public PageResponse list(BasicSearch search) {
        Page<User> users = userService.list(search.getSearchString(), search.getPageable(), Role.ADMIN);
        List<AuthUserResponse> list = users.getContent().stream().map(AuthUserResponse::of).toList();
        return PageResponse.of(list, PageInfo.of(users));
    }

    /**
     * 관리자 id로 조회
     *
     * @param id
     * @return ResponseUserDto
     */
    @Transactional(readOnly = true)
    public AuthUserResponse retrieve(long id) {
        return AuthUserResponse.of(userService.findById(id));
    }

    /**
     * 관리자 정보 수정
     *
     * @param id
     * @param adminUpdateMangerRequest
     */
    @Transactional
    public void update(long id, AdminUpdateMangerRequest adminUpdateMangerRequest) {
        userService.update(id, adminUpdateMangerRequest.getNickname(), adminUpdateMangerRequest.getPhoneNumber(), adminUpdateMangerRequest.getProfileImage());
    }

    /**
     * 관리자 삭제
     *
     * @param id
     * @param deletedBy
     */
    @Transactional
    public void delete(long id, long deletedBy) {
        userService.delete(id, deletedBy);
    }
}
