package im.fooding.app.service.manager;

import im.fooding.app.controller.admin.api.managers.dto.CreateManagerDto;
import im.fooding.app.controller.admin.api.managers.dto.LoginManagerDto;
import im.fooding.app.controller.admin.api.managers.dto.UpdateMangerDto;
import im.fooding.app.dto.user.ResponseUserDto;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.jwt.dto.TokenResponse;
import im.fooding.core.global.jwt.service.JwtService;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerApplicationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 관리자 계정 생성
     *
     * @param createManagerDto
     */
    @Transactional
    public void register(CreateManagerDto createManagerDto) {
        userService.save(
                createManagerDto.getEmail(),
                createManagerDto.getNickname(),
                passwordEncoder.encode(createManagerDto.getPassword()),
                Role.ADMIN
        );
    }

    /**
     * 관리자 로그인
     *
     * @param loginManagerDto
     * @return TokenResponse
     */
    @Transactional
    public TokenResponse login(LoginManagerDto loginManagerDto) {
        User manager = userService.findByEmailAndRole(loginManagerDto.getEmail(), Role.ADMIN);
        if (!passwordEncoder.matches(loginManagerDto.getPassword(), manager.getPassword())) {
            throw new ApiException(ErrorCode.LOGIN_FAILED);
        }
        TokenResponse tokenResponse = jwtService.issueJwtToken(manager.getId());
        manager.updatedRefreshToken(tokenResponse.getRefreshToken());
        return tokenResponse;
    }

    /**
     * 관리자 리스트 조회
     *
     * @param search
     * @return
     */
    @Transactional(readOnly = true)
    public PageResponse list(BasicSearch search) {
        Page<User> users = userService.list(search.getSearchString(), search.getPageable());
        List<ResponseUserDto> list = users.getContent().stream().map(ResponseUserDto::of).toList();
        return PageResponse.of(list, PageInfo.of(users));
    }

    /**
     * 관리자 id로 조회
     *
     * @param id
     * @return ResponseUserDto
     */
    @Transactional(readOnly = true)
    public ResponseUserDto retrieve(long id) {
        return ResponseUserDto.of(userService.findById(id));
    }

    /**
     * 관리자 정보 수정
     *
     * @param id
     * @param updateMangerDto
     */
    @Transactional
    public void update(long id, UpdateMangerDto updateMangerDto) {
        userService.update(id, updateMangerDto.getNickname(), updateMangerDto.getPhoneNumber(), updateMangerDto.getProfileImage());
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
